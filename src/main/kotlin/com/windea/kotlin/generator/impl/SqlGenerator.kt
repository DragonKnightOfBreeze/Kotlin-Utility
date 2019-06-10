@file:Suppress("UNCHECKED_CAST")

package com.windea.kotlin.generator.impl

import com.windea.kotlin.annotation.NotTested
import com.windea.kotlin.generator.ITextGenerator
import com.windea.kotlin.utils.JsonUtils
import com.windea.kotlin.utils.YamlUtils
import java.nio.file.Files
import java.nio.file.Path
import java.text.SimpleDateFormat
import java.util.*

/**
 * Sql语句的生成器。
 */
@NotTested
class SqlGenerator private constructor() : ITextGenerator {
	private val inputMap = mutableMapOf<String, Any?>()
	private var outputText = "-- Generated from kotlin script written by DragonKnightOfBreeze.\n"
	
	
	override fun execute(): SqlGenerator {
		generateSchemaText()
		generateDataText()
		return this
	}
	
	private fun generateSchemaText() {
		//NOTE 不生成，因为Spring Boot可以动态创建表
	}
	
	private fun generateDataText() {
		val databaseName = inputMap.keys.first()
		val database = inputMap[databaseName] as Map<String, List<Map<String, Any?>>>
		
		outputText += """
		|use $databaseName;
		|
		|${database.entries.joinToString("\n\n") { (tableName, table) ->
			val columnNameSnippet = table[0].keys.joinToString(", ")
			
			"""
			|insert into $tableName ($columnNameSnippet) values
			|${table.joinToString(",\n", "", ";\n") { data ->
				val dataSnippet = data.values.joinToString(", ") { column -> escapeText(quoteText(column)) }
				
				"\t($dataSnippet)"
			}}
			""".trimMargin()
		}}
		""".trimMargin()
	}
	
	private fun quoteText(text: Any?): String {
		return when(text) {
			is String -> "'$text'"
			is Date -> "'${SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(text)}'"
			else -> text.toString()
		}
	}
	
	private fun escapeText(text: String): String {
		return text.replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t")
	}
	
	override fun generate(outputPath: String) {
		Files.writeString(Path.of(outputPath), outputText)
	}
	
	
	companion object {
		/**
		 * 从指定路径 [inputPath] 的json文件读取输入映射。
		 *
		 * 文件结构参考：`/{DatabaseName}/{table_name}/[]/{column_name}`。
		 */
		@JvmStatic
		fun fromJson(inputPath: String): SqlGenerator {
			val generator = SqlGenerator()
			generator.inputMap += JsonUtils.fromFile(inputPath)
			return generator
		}
		
		/**
		 * 从指定路径 [inputPath] 的yaml文件读取输入映射。
		 *
		 * 文件结构参考：`/{DatabaseName}/{table_name}/[]/{column_name}`。
		 */
		@JvmStatic
		fun fromYaml(inputPath: String): SqlGenerator {
			val generator = SqlGenerator()
			generator.inputMap += YamlUtils.fromFile(inputPath)
			return generator
		}
	}
}
