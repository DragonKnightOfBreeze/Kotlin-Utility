package com.windea.commons.kotlin.loader

import com.windea.commons.kotlin.extension.getPathInfo

/**
 * 数据类型转化器。
 */
object DataConverter {
	/**
	 * 将一种数据文件转换成另一种数据文件，分别指定输入、输出路径。
	 */
	fun convert(fromType: DataType, toType: DataType, fromPath: String, toPath: String) {
		val dataMap = fromType.loader.fromFile(fromPath)
		toType.loader.toFile(dataMap, toPath)
	}
	
	/**
	 * 将一种数据文件转换成另一种数据文件，输入与输出路径仅扩展名不同。
	 */
	fun convert(fromType: DataType, toType: DataType, fromPath: String) {
		val toPath = fromPath.getPathInfo().changeFileExt(toType.fileExt)
		val dataMap = fromType.loader.fromFile(fromPath)
		toType.loader.toFile(dataMap, toPath)
	}
}