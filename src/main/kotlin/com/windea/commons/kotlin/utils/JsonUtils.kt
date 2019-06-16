@file:Suppress("UNCHECKED_CAST")

package com.windea.commons.kotlin.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.FileReader
import java.nio.file.Files
import java.nio.file.Path


/**
 * Json文件的工具类。
 */
object JsonUtils {
	private var gsonBuilder = GsonBuilder()
	
	init {
		gsonBuilder.serializeNulls().setPrettyPrinting()
	}
	
	
	fun configureGsonBuidler(handler: (gsonBuilder: GsonBuilder) -> Unit): JsonUtils {
		handler.invoke(gsonBuilder)
		return this
	}
	
	fun <T> fromFile(path: String, type: Class<T>): T {
		val reader = FileReader(path)
		return json().fromJson(reader, type)
	}
	
	fun fromFile(path: String): Map<String, Any?> {
		return fromFile(path, Map::class.java) as Map<String, Any?>
	}
	
	fun <T> fromString(string: String, type: Class<T>): T {
		return json().fromJson(string, type)
	}
	
	fun fromString(string: String): Map<String, Any?> {
		return fromString(string, Map::class.java) as Map<String, Any?>
	}
	
	
	fun <T> toFile(data: T, path: String) {
		Files.writeString(Path.of(path), json().toJson(data))
	}
	
	fun <T> toString(data: T): String {
		return json().toJson(data)
	}
	
	
	private fun json(): Gson {
		return gsonBuilder.create()
	}
}
