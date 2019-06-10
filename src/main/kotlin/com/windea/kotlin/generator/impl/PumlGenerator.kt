package com.windea.kotlin.generator.impl

import com.windea.kotlin.generator.ITextGenerator
import com.windea.kotlin.utils.JsonUtils
import com.windea.kotlin.utils.YamlUtils
import java.nio.file.Files
import java.nio.file.Path

/**
 * Plant Uml代码的生成器。
 */
class PumlGenerator private constructor() : ITextGenerator {
	private val inputMap = mutableMapOf<String, Any?>()
	private var outputText = "Generated from kotlin script written by DragonKnightOfBreeze.\n"
	
	
	override fun execute(): ITextGenerator {
		TODO()
	}
	
	override fun generate(outputPath: String) {
		Files.writeString(Path.of(outputPath), outputText)
	}
	
	
	companion object {
		/**
		 * 从指定路径 [inputPath] 的json文件读取输入映射。
		 */
		@JvmStatic
		fun fromJson(inputPath: String): PumlGenerator {
			val generator = PumlGenerator()
			generator.inputMap += JsonUtils.fromFile(inputPath)
			return generator
		}
		
		/**
		 * 从指定路径 [inputPath] 的yaml文件读取输入映射。
		 */
		@JvmStatic
		fun fromYaml(inputPath: String): PumlGenerator {
			val generator = PumlGenerator()
			generator.inputMap += YamlUtils.fromFile(inputPath)
			return generator
		}
	}
}
