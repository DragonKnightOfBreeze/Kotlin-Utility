package com.windea.commons.kotlin.loader

/**
 * 数据读取器的接口。
 */
interface DataLoader {
	fun <T : Any> fromFile(path: String, type: Class<T>): T
	
	fun fromFile(path: String): Map<String, Any?>
	
	fun <T : Any> fromString(string: String, type: Class<T>): T
	
	fun fromString(string: String): Map<String, Any?>
	
	fun <T : Any> toFile(data: T, path: String)
	
	fun <T : Any> toString(data: T): String
}
