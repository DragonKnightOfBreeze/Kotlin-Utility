@file:Suppress("UNCHECKED_CAST")

package com.windea.commons.kotlin.loader.impl

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.windea.commons.kotlin.loader.JsonLoader
import java.io.File
import java.io.FileReader
import java.lang.reflect.Type

class GsonJsonLoader : JsonLoader {
	private val gsonBuilder = GsonBuilder()
	
	init {
		gsonBuilder.serializeNulls().setPrettyPrinting()
	}
	
	
	class GenericType<T> : TypeToken<T>()
	
	private fun <T> getGenericType(): Type {
		return GenericType<T>().type
	}
	
	
	private fun gson(): Gson {
		return gsonBuilder.create()
	}
	
	
	fun configureGsonBuilder(handler: (gsonBuilder: GsonBuilder) -> Unit): GsonJsonLoader {
		handler.invoke(gsonBuilder)
		return this
	}
	
	
	override fun <T : Any> fromFile(path: String, type: Class<T>): T {
		val reader = FileReader(path)
		return gson().fromJson(reader, type)
	}
	
	override fun fromFile(path: String): Map<String, Any?> {
		return fromFile(path, Map::class.java) as Map<String, Any?>
	}
	
	override fun <T : Any> fromString(string: String, type: Class<T>): T {
		return gson().fromJson(string, type)
	}
	
	override fun fromString(string: String): Map<String, Any?> {
		return fromString(string, Map::class.java) as Map<String, Any?>
	}
	
	override fun <T : Any> toFile(data: T, path: String) {
		val string = toString(data)
		File(path).writeText(string)
	}
	
	override fun <T : Any> toString(data: T): String {
		return gson().toJson(data)
	}
}
