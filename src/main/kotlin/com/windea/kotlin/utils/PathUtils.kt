package com.windea.kotlin.utils

import java.util.*

/**
 * 路径的工具类。
 */
object PathUtils {
	/**
	 * 根据路径片段 [paths]，组成完整路径。允许混合两种分隔符。
	 */
	fun join(vararg paths: String): String {
		return paths.joinToString("\\").replace("", "\\")
	}
	
	/**
	 * 根据文件路径 [filePath]，得到文件名。
	 */
	fun getFileName(filePath: String): String {
		val index = filePath.lastIndexOf("\\")
		return if(index == -1) filePath else filePath.substring(index + 1)
	}
	
	/**
	 * 根据文件路径 [filePath]，得到切去扩展名的文件路径。123.123
	 */
	fun subFileExt(filePath: String): String {
		val index = filePath.lastIndexOf(".")
		return if(index == -1) filePath else filePath.substring(0, index)
	}
	
	/**
	 * 根据文件路径 [filePath]，得到带有"."的文件扩展名。如果没有，则返回空字符串。
	 */
	fun getFileExt(filePath: String): String {
		val index = filePath.lastIndexOf(".")
		return if(index == -1) "" else filePath.substring(index)
	}
	
	/**
	 * 根据文件路径 [filePath] 和新扩展名 [newFileExt]，更改带有"."的文件扩展名。
	 */
	fun changeFileExt(filePath: String, newFileExt: String): String {
		return subFileExt(filePath) + newFileExt
	}
	
	/**
	 * 生成随机的文件名（保留扩展名）。
	 */
	fun getRandomFileName(fileName: String): String {
		val uuid = UUID.randomUUID().toString()
		//尝试得到扩展名
		val index = fileName.lastIndexOf(".")
		return if(index != -1) uuid + fileName.substring(index) else uuid
	}
	
	/**
	 * 生成随机的文件目录名（一级+二级）。
	 */
	fun getRandomDirName(fileName: String): String {
		val hashCode = fileName.hashCode()
		//一级目录
		val d1 = hashCode and 0xf
		//二级目录
		val d2 = hashCode shr 4 and 0xf
		return "/$d1/$d2"
	}
}
