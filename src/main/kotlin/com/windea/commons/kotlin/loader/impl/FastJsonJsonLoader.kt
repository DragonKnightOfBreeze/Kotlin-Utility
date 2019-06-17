@file:Suppress("UNCHECKED_CAST")

package com.windea.commons.kotlin.loader.impl

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.TypeReference
import com.alibaba.fastjson.parser.ParserConfig
import com.alibaba.fastjson.serializer.SerializeConfig
import com.windea.commons.kotlin.loader.JsonLoader
import java.io.FileReader
import java.io.FileWriter
import java.lang.reflect.Type

class FastJsonJsonLoader : JsonLoader {
	private val parserConfig = ParserConfig()
	private val serializeConfig = SerializeConfig()
	
	
	class GenericType<T> : TypeReference<T>()
	
	private fun <T> getGenericType(): Type {
		return GenericType<T>().type
	}
	
	
	fun configureParseConfig(handler: (ParserConfig) -> Unit): FastJsonJsonLoader {
		handler.invoke(parserConfig)
		return this
	}
	
	fun configureSerializeConfig(handler: (SerializeConfig) -> Unit): FastJsonJsonLoader {
		handler.invoke(serializeConfig)
		return this
	}
	
	
	override fun <T : Any> fromFile(path: String, type: Class<T>): T {
		val string = FileReader(path).readText()
		return fromString(string, type)
	}
	
	override fun fromFile(path: String): Map<String, Any?> {
		val string = FileReader(path).readText()
		return fromString(string, Map::class.java) as Map<String, Any?>
	}
	
	override fun <T : Any> fromString(string: String, type: Class<T>): T {
		return JSON.parseObject(string, type, parserConfig)
	}
	
	override fun fromString(string: String): Map<String, Any?> {
		return fromString(string, Map::class.java) as Map<String, Any?>
	}
	
	override fun <T : Any> toFile(data: T, path: String) {
		val string = toString(data)
		FileWriter(path).write(string)
	}
	
	override fun <T : Any> toString(data: T): String {
		return JSON.toJSONString(data, serializeConfig)
	}
}
