@file:Suppress("UNCHECKED_CAST")

package com.windea.commons.kotlin.utils

import org.yaml.snakeyaml.DumperOptions
import org.yaml.snakeyaml.LoaderOptions
import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor.Constructor
import org.yaml.snakeyaml.representer.Representer
import java.io.FileReader
import java.io.FileWriter
import java.util.*

/**
 * Yaml文件的工具类。
 */
object YamlUtils {
	private val constructor = Constructor()
	private val representer = Representer()
	private var loaderOptions = LoaderOptions()
	private var dumperOptions = DumperOptions()
	
	init {
		loaderOptions.isAllowDuplicateKeys = false
		dumperOptions.isAllowReadOnlyProperties = true
		dumperOptions.indent = 2
		dumperOptions.timeZone = TimeZone.getTimeZone("GMT-8:00")
		dumperOptions.width = 120
		dumperOptions.isAllowUnicode = true
	}
	
	
	fun configureLoaderOptions(handler: (options: LoaderOptions) -> Unit): YamlUtils {
		handler.invoke(loaderOptions)
		return this
	}
	
	fun configureDumperOptions(handler: (options: DumperOptions) -> Unit): YamlUtils {
		handler.invoke(dumperOptions)
		return this
	}
	
	fun addTags(tagMap: Map<String, String>): YamlUtils {
		dumperOptions.tags.putAll(tagMap)
		return this
	}

	
	fun <T> fromFile(path: String, type: Class<T>): T {
		val reader = FileReader(path)
		return yaml().loadAs(reader, type)
	}
	
	fun fromFile(path: String): Map<String, Any?> {
		return fromFile(path, Map::class.java) as Map<String, Any?>
	}
	
	fun fromFileAll(path: String): List<Any> {
		val reader = FileReader(path)
		val resultList = ArrayList<Any>()
		for(elem in yaml().loadAll(reader)) {
			resultList.add(elem)
		}
		return resultList
	}
	
	fun <T> fromString(string: String, type: Class<T>): T {
		return yaml().loadAs(string, type)
	}
	
	fun fromString(string: String): Map<String, Any?> {
		return fromString(string, Map::class.java) as Map<String, Any?>
	}
	
	fun fromStringAll(string: String): List<Any> {
		val resultList = ArrayList<Any>()
		for(elem in yaml().loadAll(string)) {
			resultList.add(elem)
		}
		return resultList
	}
	
	
	fun <T> toFile(data: T, path: String) {
		val writer = FileWriter(path)
		yaml().dump(data, writer)
	}
	
	fun <T> toFileAll(dataList: List<T>, path: String) {
		val writer = FileWriter(path)
		yaml().dumpAll(dataList.iterator(), writer)
	}
	
	fun <T> toString(data: T): String {
		return yaml().dump(data)
	}
	
	fun <T> toStringAll(dataList: List<T>): String {
		return yaml().dumpAll(dataList.iterator())
	}
	
	
	private fun yaml(): Yaml {
		return Yaml(constructor, representer, dumperOptions, loaderOptions)
	}
}
