@file:Suppress("UNCHECKED_CAST")

package com.windea.kotlin.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonWriter

import java.io.FileReader
import java.io.FileWriter

/**
 * Json文件的工具类。
 */
object JsonUtils {
	/**
	 * 从指定的文件路径 [path] 读取json数据，返回一个映射。
	 */
	@Throws(Exception::class)
	fun fromFile(path: String): Map<String, Any?> {
		return fromFile(path, Map::class.java) as Map<String, Any?>
	}
	
	/**
	 * 从指定的文件路径 [path] 读取json数据，返回一个泛型对象。
	 */
	@Throws(Exception::class)
	fun <T> fromFile(path: String, type: Class<T>): T {
		val reader = FileReader(path)
		return json().fromJson(reader, type)
	}
	
	/**
	 * 从指定的json字符串 [string] 读取json数据，返回一个映射。
	 */
	fun fromString(string: String): Map<String, Any?> {
		return fromString(string, Map::class.java) as Map<String, Any?>
	}
	
	/**
	 * 从指定的json字符串 [string] 读取json数据，返回一个泛型对象。
	 */
	fun <T> fromString(string: String, type: Class<T>): T {
		return json().fromJson(string, type)
	}
	
	/**
	 * 将指定的泛型对象 [data] 写入指定路径 [path] 的json文件。
	 */
	@Throws(Exception::class)
	fun <T> toFile(data: T, path: String) {
		toFile(data, path, 2)
	}
	
	/**
	 * 将指定的泛型对象 [data] 写入指定路径 [path] 的json文件。指定缩进 [indent]。
	 */
	@Throws(Exception::class)
	fun <T> toFile(data: T, path: String, indent: Int) {
		val writer = JsonWriter(FileWriter(path))
		val type = object : TypeToken<T>() {}.type
		writer.setIndent(" ".repeat(indent))
		json().toJson(data, type, writer)
	}
	
	/**
	 * 将指定的泛型对象 [data] 写入json字符串，然后返回。
	 */
	fun <T> toString(data: T): String {
		return json().toJson(data)
	}
	
	
	private fun json(): Gson {
		return Gson()
	}
}
