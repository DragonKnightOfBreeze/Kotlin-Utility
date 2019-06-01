@file:Suppress("UNCHECKED_CAST")

package com.windea.kotlin.generator.impl

import com.windea.kotlin.annotation.NotTested
import com.windea.kotlin.extension.query
import com.windea.kotlin.generator.ITextGenerator
import com.windea.kotlin.generator.JsonSchemaRule
import com.windea.kotlin.utils.JsonUtils
import com.windea.kotlin.utils.YamlUtils
import java.util.concurrent.ConcurrentHashMap

/**
 * Json Schema的生成器。
 */
@NotTested
class JsonSchemaGenerator private constructor() : ITextGenerator {
	private val inputMap = mutableMapOf<String, Any?>()
	private val dataMap = mutableMapOf<String, Any?>()
	private val ruleMap = mutableMapOf<String, JsonSchemaRule>()
	
	
	/**
	 * 从指定路径 [dataPath]的yaml文件读取数据映射。读取失败时返回空映射。
	 */
	fun loadDataMapFromYaml(dataPath: String): JsonSchemaGenerator {
		dataMap += runCatching { YamlUtils.fromFile(dataPath) }.getOrDefault(mapOf())
		return this
	}
	
	/**
	 * 从指定路径 [dataPath]的json文件读取数据映射。读取失败时返回空映射。
	 */
	fun loadDataMapFromJson(dataPath: String): JsonSchemaGenerator {
		dataMap += runCatching { JsonUtils.fromFile(dataPath) }.getOrDefault(mapOf())
		return this
	}
	
	/**
	 * 添加规则。
	 */
	fun addRules(rules: Map<String, JsonSchemaRule>): JsonSchemaGenerator {
		//在这里使用+=作为替代或出现方法调用冲突错误，不知道为什么？
		ruleMap.putAll(rules)
		return this
	}
	
	override fun execute(): JsonSchemaGenerator {
		addDefaultRules()
		convertRules(inputMap)
		return this
	}
	
	private fun addDefaultRules() {
		ruleMap.putAll(mutableMapOf(
			"language" to { (_, value) ->
				//更改为Idea扩展规则
				mapOf("x-intellij-language-injection" to value)
			},
			"deprecated" to { (_, value) ->
				//更改为Idea扩展规则
				when(value) {
					is String -> mapOf("deprecationMessage" to value)
					true -> mapOf("deprecationMessage" to "")
					else -> mapOf()
				}
			},
			"enumSchema" to { (_, value) ->
				//提取路径`enumSchema/value`对应的值列表
				val enumConsts = (value as List<Map<String, Any?>>).map { it["value"] }
				mapOf("enum" to enumConsts)
			},
			"generatedFrom" to { (_, value) ->
				//提取$dataMap中的路径`$value`对应的值列表
				val enumConsts = dataMap.query(value as String)
				when {
					enumConsts.isNotEmpty() -> mapOf("enum" to enumConsts)
					else -> mapOf()
				}
			}
		))
	}
	
	//递归遍历整个约束映射的深复制，处理原本的约束映射
	//如果找到了自定义规则，则替换成规则集合中指定的官方规则
	//使用并发映射解决java.util.ConcurrentModificationException
	private fun convertRules(map: MutableMap<String, Any?>) {
		for((key, value) in ConcurrentHashMap<String, Any?>(map)) {
			//如果值为映射，则继续向下递归遍历，否则检查是否匹配规则名
			if(value is Map<*, *>) {
				convertRules(value as MutableMap<String, Any?>)
			} else {
				//如果找到了对应规则名的规则，则执行规则并替换
				ruleMap[key]?.let {
					val newRule = it.invoke(Pair(key, value))
					//居然还能直接这样写？
					map -= key
					map += newRule
				}
			}
		}
	}
	
	override fun generate(outputPath: String) {
		JsonUtils.toFile(inputMap, outputPath)
	}
	
	
	companion object {
		/**
		 * 从指定路径 [inputPath] 的扩展json schema文件读取输入映射。
		 */
		@JvmStatic
		fun fromExtJsonSchema(inputPath: String): JsonSchemaGenerator {
			val generator = JsonSchemaGenerator()
			generator.inputMap += JsonUtils.fromFile(inputPath)
			return generator
		}
		
		/**
		 * 从指定路径 [inputPath] 的扩展yaml schema文件读取输入映射。
		 */
		@JvmStatic
		fun fromExtYamlSchema(inputPath: String): JsonSchemaGenerator {
			val generator = JsonSchemaGenerator()
			generator.inputMap += YamlUtils.fromFile(inputPath)
			return generator
		}
	}
}
