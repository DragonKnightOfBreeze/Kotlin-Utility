package com.windea.utility.common.generators.impl

import com.windea.utility.common.annotations.marks.*
import com.windea.utility.common.generators.*
import com.windea.utility.common.loaders.*
import java.io.*

/**PlantUml代码的生成器。*/
@NotTested
class PlantUmlGenerator : TextGenerator {
	private val inputMap = mutableMapOf<String, Any?>()
	private var outputText = ""
	
	
	/**@param inputType Json, Yaml*/
	override fun from(inputPath: String, inputType: String): PlantUmlGenerator {
		when(inputType) {
			"Json" -> this.inputMap += JsonLoader.instance.fromFile(inputPath)
			"Yaml" -> this.inputMap += YamlLoader.instance.fromFile(inputPath)
			else -> throw IllegalArgumentException(Messages.invalidInputType)
		}
		return this
	}
	
	fun from(inputPath: String) = from(inputPath, "Yaml")
	
	
	/**@param generateStrategy Uml, UmlMarkdown*/
	override fun generate(generateStrategy: String): PlantUmlGenerator {
		when(generateStrategy) {
			"Uml" -> generateUml()
			"UmlMarkdown" -> generateUmlMarkdown()
			else -> throw IllegalArgumentException(Messages.invalidGenerateStrategy)
		}
		return this
	}
	
	private fun generateUml() {
		TODO()
	}
	
	private fun generateUmlMarkdown() {
		TODO()
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
