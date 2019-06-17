package com.windea.commons.kotlin.loader.impl

import com.windea.commons.kotlin.annotation.NotTested
import com.windea.commons.kotlin.extension.getPaths
import com.windea.commons.kotlin.extension.getValueByPath
import com.windea.commons.kotlin.extension.setValueByPath
import com.windea.commons.kotlin.loader.JsonLoader
import com.windea.commons.kotlin.loader.Messages
import com.windea.commons.kotlin.loader.PropertiesLoader
import java.io.FileReader
import java.io.FileWriter
import java.io.StringReader
import java.util.*

//TODO： 是否能够正常读取UTF-8编码的文件，同时也能正常读取ISO-8859-1编码的文件？

class StandardPropertiesLoader : PropertiesLoader {
	@NotTested
	private fun getDataMap(properties: Properties): Map<String, Any?> {
		val dataMap = mutableMapOf<String, Any>()
		val names = properties.stringPropertyNames()
		for(name in names) {
			dataMap.setValueByPath(name, properties.getProperty(name))
		}
		return dataMap
	}
	
	override fun <T : Any> fromFile(path: String, type: Class<T>): T {
		throw NotImplementedError(Messages.notSupportedYet)
	}
	
	override fun fromFile(path: String): Map<String, Any?> {
		val properties = Properties()
		properties.load(FileReader(path))
		return getDataMap(properties)
	}
	
	override fun <T : Any> fromString(string: String, type: Class<T>): T {
		throw NotImplementedError(Messages.notSupportedYet)
	}
	
	override fun fromString(string: String): Map<String, Any?> {
		val properties = Properties()
		properties.load(StringReader(string))
		return getDataMap(properties)
	}
	
	override fun <T : Any> toFile(data: T, path: String) {
		val string = toString(data)
		FileWriter(path).write(string)
	}
	
	@NotTested
	override fun <T : Any> toString(data: T): String {
		//使用JsonLoader，而非反射
		val dataMap = JsonLoader.instance.fromString(JsonLoader.instance.toString(data))
		val paths = dataMap.getPaths()
		return paths.map { "$it: ${dataMap.getValueByPath(it)}" }.reduce { a, b -> "$a\r\n$b" }
	}
}
