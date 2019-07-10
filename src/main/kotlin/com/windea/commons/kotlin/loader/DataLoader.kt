package com.windea.commons.kotlin.loader

//TODO 允许读取指定泛型类型的数据，并且保证较少的api

/**
 * 数据读取器的接口。
 */
interface DataLoader {
	/**
	 * 从指定路径[path]的文件加载数据。指定数据类型[type]。
	 */
	fun <T : Any> fromFile(path: String, type: Class<T>): T
	
	/**
	 * 从指定路径[path]的文件加载数据。
	 */
	fun fromFile(path: String): Map<String, Any?>
	
	/**
	 * 从指定字符串[string]加载数据。指定数据类型[type]。
	 */
	fun <T : Any> fromString(string: String, type: Class<T>): T
	
	/**
	 * 从指定字符串[string]加载数据。
	 */
	fun fromString(string: String): Map<String, Any?>
	
	/**
	 * 存储数据[data]到指定路径[path]的文件。
	 */
	fun <T : Any> toFile(data: T, path: String)
	
	/**
	 * 存储数据[data]到字符串。
	 */
	fun <T : Any> toString(data: T): String
}
