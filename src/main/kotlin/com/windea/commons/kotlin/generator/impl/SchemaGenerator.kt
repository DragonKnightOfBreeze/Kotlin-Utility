@file:Suppress("UNCHECKED_CAST")

package com.windea.commons.kotlin.generator.impl

import com.windea.commons.kotlin.extension.query
import com.windea.commons.kotlin.generator.ITextGenerator
import com.windea.commons.kotlin.generator.Messages
import com.windea.commons.kotlin.utils.JsonUtils
import com.windea.commons.kotlin.utils.YamlUtils
import java.util.concurrent.ConcurrentHashMap

typealias SchemaRule = (originRule: Pair<String, Any?>) -> Map<String, Any?>

/**
 * Json/Yaml Schema的生成器。
 */
class JsonSchemaGenerator private constructor() : ITextGenerator {
	private val inputMap = mutableMapOf<String, Any?>()
	private val dataMap = mutableMapOf<String, Any?>()
	private val ruleMap = mutableMapOf<String, SchemaRule>()
	
	private val multiSchemaRuleNames = listOf("oneOf", "allOf", "anyOf")
	
	
	/**
	 * @param inputType ExtendedJsonSchema, ExtendedYamlSchema
	 */
	override fun from(inputPath: String, inputType: String): ITextGenerator {
		when(inputType) {
			"ExtendedJsonSchema" -> this.inputMap += JsonUtils.fromFile(inputPath)
			"ExtendedYamlSchema" -> this.inputMap += YamlUtils.fromFile(inputPath)
			else -> throw IllegalArgumentException(Messages.invalidInputType)
		}
		this.ruleMap += getDefaultRules()
		return this
	}
	
	private fun getDefaultRules(): Map<String, SchemaRule> {
		return mapOf(
			"\$ref" to { (_, value) ->
				//将对yaml schema文件的引用改为对json schema文件的引用
				val newValue = (value as String).replace(".yml", ".json").replace(".yaml", ".json")
				mapOf("\$ref" to newValue)
			},
			"\$gen" to { (_, value) ->
				//提取$dataMap中的路径`$value`对应的值列表
				val newValue = dataMap.query(value as String)
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
	}
	
	
	fun loadDataMap(dataPath: String, dataType: String): ITextGenerator {
		runCatching {
			when(dataType) {
				"Json" -> this.inputMap += JsonUtils.fromFile(dataPath)
				"Yaml" -> this.inputMap += YamlUtils.fromFile(dataPath)
			}
		}
		return this
	}
	
	fun addRules(rules: Map<String, SchemaRule>): ITextGenerator {
		//在这里使用+=作为替代或出现方法调用冲突错误，不知道为什么？
		ruleMap.putAll(rules)
		return this
	}
	
	
	/**
	 * @param generateStrategy  Default
	 */
	override fun generate(generateStrategy: String): ITextGenerator {
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
	
	
	/**
	 * @param outputType JsonSchema, YamlSchema
	 */
	override fun to(outputPath: String, outputType: String) {
		when(outputType) {
			"JsonSchema" -> JsonUtils.toFile(inputMap, outputPath)
			"YamlSchema" -> YamlUtils.toFile(inputMap, outputPath)
			else -> throw IllegalArgumentException(Messages.invalidOutputType)
		}
	}
}
