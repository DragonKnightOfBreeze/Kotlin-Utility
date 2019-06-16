package com.windea.commons.kotlin.loader

/**
 * 数据读取器的接口。
 */
interface DataLoader {
	fun <T> fromFile(path: String, type: Class<T>): T
	
	fun fromFile(path: String): Map<String, Any?>
	
	fun <T> fromString(string: String, type: Class<T>): T
	
	fun fromString(string: String): Map<String, Any?>
	
	fun <T> toFile(data: T, path: String)
	
	fun <T> toString(data: T): String
}
