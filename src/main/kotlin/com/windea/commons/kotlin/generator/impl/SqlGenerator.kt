@file:Suppress("UNCHECKED_CAST")

package com.windea.commons.kotlin.generator.impl

import com.windea.commons.kotlin.generator.Messages
import com.windea.commons.kotlin.generator.TextGenerator
import com.windea.commons.kotlin.loader.JsonLoader
import com.windea.commons.kotlin.loader.YamlLoader
import java.nio.file.Files
import java.nio.file.Path
import java.text.SimpleDateFormat
import java.util.*

/**
 * Sql语句的生成器。
 */
class SqlGenerator private constructor() : TextGenerator {
	private val inputMap = mutableMapOf<String, Any?>()
	private var outputText = ""
	
	
	/**
	 * @param inputType Json, Yaml
	 */
	override fun from(inputPath: String, inputType: String): TextGenerator {
		when(inputType) {
			"Json" -> this.inputMap += JsonLoader.instance.fromFile(inputPath)
			"Yaml" -> this.inputMap += YamlLoader.instance.fromFile(inputPath)
			else -> throw IllegalArgumentException(Messages.invalidInputType)
		}
		return this
	}
	
	
	/**
	 * @param generateStrategy  Default
	 */
	override fun generate(generateStrategy: String): TextGenerator {
		when(generateStrategy) {
			"Default" -> generateSqlText()
			else -> throw IllegalArgumentException(Messages.invalidGenerateStrategy)
		}
		return this
	}
	
	fun generate() = generate("Default")
	
	private fun generateSqlText() {
		val databaseName = inputMap.keys.first()
		val database = inputMap[databaseName] as Map<String, List<Map<String, Any?>>>
		
		outputText += """
		|-- ${Messages.prefixComment}
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
	
	
	/**
	 * @param outputType Default
	 */
	override fun to(outputPath: String, outputType: String) {
		when(outputType) {
			"Default" -> Files.writeString(Path.of(outputPath), outputText)
			else -> throw IllegalArgumentException(Messages.invalidOutputType)
		}
	}
	
	fun to(outputPath: String) = to(outputPath, "Default")
}
