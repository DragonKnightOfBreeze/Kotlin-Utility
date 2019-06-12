package com.windea.commons.kotlin.generator

/**
 * 文本生成器的接口。
 */
interface ITextGenerator {
	fun from(inputPath: String, inputType: String): ITextGenerator
	
	fun generate(generateStrategy: String): ITextGenerator
	
	fun to(outputPath: String, outputType: String)
}
