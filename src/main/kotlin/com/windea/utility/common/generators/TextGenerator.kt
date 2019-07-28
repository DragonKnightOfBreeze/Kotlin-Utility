package com.windea.utility.common.generators

/**文本生成器的接口。*/
interface TextGenerator {
	fun from(inputPath: String, inputType: String): TextGenerator
	
	fun generate(generateStrategy: String): TextGenerator
	
	fun to(outputPath: String, outputType: String)
}
