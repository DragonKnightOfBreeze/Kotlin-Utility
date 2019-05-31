package com.windea.kotlin.generator

/**
 * 文本生成器的接口。
 */
interface ITextGenerator {
	/**
	 * 处理接受到的数据。需要在 [generate] 方法之前调用。
	 */
	fun execute(): ITextGenerator
	
	/**
	 * 生成文本到指定的输出路径 [outputPath]。
	 */
	fun generate(outputPath: String)
}
