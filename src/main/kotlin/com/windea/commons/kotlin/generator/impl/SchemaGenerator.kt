@file:Suppress("UNCHECKED_CAST")

package com.windea.commons.kotlin.generator.impl

import com.windea.commons.kotlin.annotation.*
import com.windea.commons.kotlin.extension.*
import com.windea.commons.kotlin.generator.*
import com.windea.commons.kotlin.loader.*
import java.util.concurrent.*

typealias SchemaRule = (originRule: Pair<String, Any?>) -> Map<String, Any?>

/**Json/Yaml Schema的生成器。*/
@NotTested("未发现")
class SchemaGenerator : TextGenerator {
	private val inputMap = mutableMapOf<String, Any?>()
	private val dataMap = mutableMapOf<String, Any?>()
	private val ruleMap = mutableMapOf<String, SchemaRule>()
	
	private val multiSchemaRuleNames = listOf("oneOf", "allOf", "anyOf")
	
	/**
	 * 默认扩展规则：
	 * * $ref: string
	 * * $gen: string
	 * * language: string
	 * * deprecated: string | boolean
	 * * enumConsts: {value: string, description: string}
	 */
	private val defaultRuleMap
		get() = mapOf<String, SchemaRule>(
			"\$ref" to { (_, value) ->
				//将对yaml schema文件的引用改为对json schema文件的引用
				val newValue = (value as String).replace(".yml", ".json").replace(".yaml", ".json")
				mapOf("\$ref" to newValue)
			},
			"\$gen" to { (_, value) ->
				//提取$dataMap中的路径`$value`对应的值列表
				val newValue = dataMap.queryValue(value as String)
				when {
					newValue.isNotEmpty() -> mapOf("enum" to newValue)
					else -> mapOf()
				}
			},
			"language" to { (_, value) ->
				//更改为Idea扩展规则
				mapOf("x-intellij-language-injection" to value as String)
			},
			"deprecated" to { (_, value) ->
				//更改为Idea扩展规则
				when(value) {
					is String -> mapOf("deprecationMessage" to value)
					true -> mapOf("deprecationMessage" to "")
					else -> mapOf()
				}
			},
			"enumConsts" to { (_, value) ->
				//提取路径`enumSchema/value`对应的值列表
				val newValue = (value as List<Map<String, Any?>>).map { it["value"] }
				when {
					newValue.isNotEmpty() -> mapOf("enum" to newValue)
					else -> mapOf()
				}
			}
		)
	
	
	/**@param inputType ExtendedJsonSchema, ExtendedYamlSchema*/
	override fun from(inputPath: String, inputType: String): SchemaGenerator {
		when(inputType) {
			"ExtendedJsonSchema" -> this.inputMap += JsonLoader.instance.fromFile(inputPath)
			"ExtendedYamlSchema" -> this.inputMap += YamlLoader.instance.fromFile(inputPath)
			else -> throw IllegalArgumentException(Messages.invalidInputType)
		}
		this.ruleMap += defaultRuleMap
		return this
	}
	
	fun from(inputPath: String) = from(inputPath, "ExtendedYamlSchema")
	
	
	/**@param dataType Json, Yaml*/
	fun loadDataMap(dataPath: String, dataType: String): SchemaGenerator {
		runCatching {
			when(dataType) {
				"Json" -> this.dataMap += JsonLoader.instance.fromFile(dataPath)
				"Yaml" -> this.dataMap += YamlLoader.instance.fromFile(dataPath)
			}
		}
		return this
	}
	
	fun addRules(rules: Map<String, SchemaRule>): TextGenerator {
		//在这里使用+=作为替代或出现方法调用冲突错误，不知道为什么？
		ruleMap.putAll(rules)
		return this
	}
	
	
	/**@param generateStrategy  Default*/
	override fun generate(generateStrategy: String): SchemaGenerator {
		when(generateStrategy) {
			"Default" -> convertRules(inputMap)
			else -> throw IllegalArgumentException(Messages.invalidGenerateStrategy)
		}
		return this
	}
	
	fun generate() = generate("Default")
	
	private fun convertRules(map: MutableMap<String, Any?>) {
		//递归遍历整个约束映射的深复制，处理原本的约束映射
		//如果找到了自定义规则，则替换成规则集合中指定的官方规则
		//使用并发映射解决java.util.ConcurrentModificationException
		//过滤掉值为null的键值对，防止NPE
		for((key, value) in ConcurrentHashMap<String, Any?>(map.filterValues { it != null })) {
			when {
				//如果值为映射，则继续向下递归遍历，否则检查是否匹配规则名
				value is Map<*, *> -> convertRules(value as MutableMap<String, Any?>)
				//考虑oneOf，allOf等情况
				key in multiSchemaRuleNames -> for(elem in value as List<MutableMap<String, Any?>>) {
					convertRules(elem)
				}
				//如果找到了对应规则名的规则，则执行规则并替换
				else -> ruleMap[key]?.let {
					val newRule = it.invoke(Pair(key, value))
					//居然还能直接这样写？
					map -= key
					map += newRule
				}
			}
		}
	}
	
	
	/**@param outputType JsonSchema, YamlSchema*/
	override fun to(outputPath: String, outputType: String) {
		when(outputType) {
			"JsonSchema" -> JsonLoader.instance.toFile(inputMap, outputPath)
			"YamlSchema" -> YamlLoader.instance.toFile(inputMap, outputPath)
			else -> throw IllegalArgumentException(Messages.invalidOutputType)
		}
	}
	
	fun to(outputPath: String) = to(outputPath, "YamlSchema")
}
