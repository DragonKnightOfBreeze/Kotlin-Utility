@file:Suppress("UNCHECKED_CAST")

package com.windea.utility.common.generators.impl

import com.windea.commons.kotlin.annotations.marks.*
import com.windea.commons.kotlin.extensions.*
import com.windea.commons.kotlin.generators.*
import com.windea.commons.kotlin.loaders.*
import java.io.*

/**Sql语句的生成器。*/
@com.windea.utility.common.annotations.marks.NotTested
class SqlGenerator : TextGenerator {
	private val inputMap = mutableMapOf<String, Any?>()
	private var outputText = ""
	
	
	/**@param inputType Json, Yaml*/
	override fun from(inputPath: String, inputType: String): SqlGenerator {
		when(inputType) {
			"Json" -> this.inputMap += JsonLoader.instance.fromFile(inputPath)
			"Yaml" -> this.inputMap += YamlLoader.instance.fromFile(inputPath)
			else -> throw IllegalArgumentException(Messages.invalidInputType)
		}
		return this
	}
	
	fun from(inputPath: String) = from(inputPath, "Yaml")
	
	
	/**@param generateStrategy  Default*/
	override fun generate(generateStrategy: String): SqlGenerator {
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
				val dataSnippet = data.values.joinToString(", ") { column ->
					column?.toString()?.wrapQuote('\'').unescape() ?: "null"
				}
				"""
				|  ($dataSnippet)
				""".trimMargin()
			}}
			""".trimMargin()
		}}
		""".trimMargin()
	}
	
	
	/**@param outputType Default*/
	override fun to(outputPath: String, outputType: String) {
		when(outputType) {
			"Default" -> File(outputPath).writeText(outputText)
			else -> throw IllegalArgumentException(Messages.invalidOutputType)
		}
	}
	
	fun to(outputPath: String) = to(outputPath, "Default")
}
