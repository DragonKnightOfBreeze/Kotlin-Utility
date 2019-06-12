package com.windea.commons.kotlin.generator.impl

import com.windea.commons.kotlin.generator.ITextGenerator
import com.windea.commons.kotlin.generator.Messages
import com.windea.commons.kotlin.utils.JsonUtils
import com.windea.commons.kotlin.utils.YamlUtils
import java.nio.file.Files
import java.nio.file.Path

/**
 * PlantUml代码的生成器。
 */
class PlantUmlGenerator private constructor() : ITextGenerator {
	private val inputMap = mutableMapOf<String, Any?>()
	private var outputText = ""
	
	
	/**
	 * @param inputType Json, Yaml
	 */
	override fun from(inputPath: String, inputType: String): ITextGenerator {
		when(inputType) {
			"Json" -> this.inputMap += JsonUtils.fromFile(inputPath)
			"Yaml" -> this.inputMap += YamlUtils.fromFile(inputPath)
			else -> throw IllegalArgumentException(Messages.invalidInputType)
		}
		return this
	}
	
	
	/**
	 * @param generateStrategy Puml, PumlMarkdown
	 */
	override fun generate(generateStrategy: String): ITextGenerator {
		when(generateStrategy) {
			"Puml" -> generatePuml()
			"PumlMarkdown" -> generatePumlMarkdown()
			else -> throw IllegalArgumentException(Messages.invalidGenerateStrategy)
		}
		return this
	}
	
	private fun generatePuml() {
		TODO()
	}
	
	private fun generatePumlMarkdown() {
		TODO()
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
