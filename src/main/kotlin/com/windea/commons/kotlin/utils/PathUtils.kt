package com.windea.commons.kotlin.utils

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
