@file:Suppress("UNCHECKED_CAST")

package com.windea.commons.kotlin.loader.impl

import com.alibaba.fastjson.*
import com.alibaba.fastjson.parser.*
import com.alibaba.fastjson.serializer.*
import com.windea.commons.kotlin.loader.*
import java.io.*

class FastJsonJsonLoader : JsonLoader {
	private val parserConfig = ParserConfig()
	private val serializeConfig = SerializeConfig()
	
	
	fun configureParseConfig(handler: (ParserConfig) -> Unit): FastJsonJsonLoader {
		handler.invoke(parserConfig)
		return this
	}
	
	fun configureSerializeConfig(handler: (SerializeConfig) -> Unit): FastJsonJsonLoader {
		handler.invoke(serializeConfig)
		return this
	}
	
	
	override fun <T : Any> fromFile(path: String, type: Class<T>): T {
		val string = File(path).readText()
		return fromString(string, type)
	}
	
	override fun fromFile(path: String): Map<String, Any?> {
		val string = File(path).readText()
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
		File(path).writeText(string)
	}
	
	override fun <T : Any> toString(data: T): String {
		return JSON.toJSONString(data, serializeConfig)
	}
}
