@file:Suppress("UNCHECKED_CAST")

package com.windea.commons.kotlin.loader.impl

import com.google.gson.GsonBuilder
import com.windea.commons.kotlin.loader.JsonLoader
import java.io.FileReader
import java.nio.file.Files
import java.nio.file.Path

class GsonJsonLoader : JsonLoader {
	private val gsonBuilder = GsonBuilder()
	private val gson = gsonBuilder.create()
	
	init {
		gsonBuilder.serializeNulls().setPrettyPrinting()
	}
	
	
	fun configureGsonBuidler(handler: (gsonBuilder: GsonBuilder) -> Unit): GsonJsonLoader {
		handler.invoke(gsonBuilder)
		return this
	}
	
	
	override fun <T : Any> fromFile(path: String, type: Class<T>): T {
		val reader = FileReader(path)
		return gson.fromJson(reader, type)
	}
	
	override fun fromFile(path: String): Map<String, Any?> {
		return fromFile(path, Map::class.java) as Map<String, Any?>
	}
	
	override fun <T : Any> fromString(string: String, type: Class<T>): T {
		return gson.fromJson(string, type)
	}
	
	override fun fromString(string: String): Map<String, Any?> {
		return fromString(string, Map::class.java) as Map<String, Any?>
	}
	
	override fun <T : Any> toFile(data: T, path: String) {
		Files.writeString(Path.of(path), gson.toJson(data))
	}
	
	override fun <T : Any> toString(data: T): String {
		return gson.toJson(data)
	}
}
