package com.windea.commons.kotlin.loader.impl

import com.windea.commons.kotlin.annotation.NotTested
import com.windea.commons.kotlin.extension.flatMapByPath
import com.windea.commons.kotlin.loader.JsonLoader
import com.windea.commons.kotlin.loader.Messages
import com.windea.commons.kotlin.loader.PropertiesLoader
import java.io.FileReader
import java.io.FileWriter
import java.io.StringReader
import java.util.*

//TODO： 是否能够正常读取UTF-8编码的文件，同时也能正常读取ISO-8859-1编码的文件？

@NotTested
class StandardPropertiesLoader : PropertiesLoader {
	private var separator = "="
	
	
	fun setSeparator(separator: String) {
		if(separator.trim() !in arrayOf(":", "=")) {
			throw IllegalArgumentException(Messages.incorrectFormat)
		}
		this.separator = separator
	}
	
	
	override fun <T : Any> fromFile(path: String, type: Class<T>): T {
		throw NotImplementedError(Messages.notSupportedYet)
	}
	
	override fun fromFile(path: String): Map<String, Any?> {
		val properties = Properties()
		properties.load(FileReader(path))
		return properties.flatMapByPath()
	}
	
	override fun <T : Any> fromString(string: String, type: Class<T>): T {
		throw NotImplementedError(Messages.notSupportedYet)
	}
	
	override fun fromString(string: String): Map<String, Any?> {
		val properties = Properties()
		properties.load(StringReader(string))
		return properties.flatMapByPath()
	}
	
	override fun <T : Any> toFile(data: T, path: String) {
		val string = toString(data)
		FileWriter(path).write(string)
	}
	
	override fun <T : Any> toString(data: T): String {
		//使用JsonLoader，而非反射
		val propMap = JsonLoader.instance.fromString(JsonLoader.instance.toString(data))
		val dataMap = propMap.flatMapByPath()
		return dataMap.map { (key, value) -> "$key: $value" }.reduce { a, b -> "$a\r\n$b" }
	}
}
