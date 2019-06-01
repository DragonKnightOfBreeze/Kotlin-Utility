package com.windea.kotlin.generator.impl

import com.windea.kotlin.generator.ITextGenerator
import java.nio.file.Files
import java.nio.file.Path
import java.util.concurrent.ConcurrentHashMap

/**
 * Markdown文本的生成器。
 */
class MarkdownGenerator private constructor() : ITextGenerator {
	private val inputMap: MutableMap<String, Any?> = ConcurrentHashMap()
	private var outputText = "<!-- Generated from kotlin script written by DragonKnightOfBreeze.\n -->"
	
	
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
		fun fromJson(inputPath: String): MarkdownGenerator {
			TODO()
		}
		
		/**
		 * 从指定路径 [inputPath] 的yaml文件读取输入映射。
		 */
		@JvmStatic
		fun fromYaml(inputPath: String): MarkdownGenerator {
			TODO()
		}
	}
}
