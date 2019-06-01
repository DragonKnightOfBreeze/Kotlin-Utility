@file:Suppress("UNCHECKED_CAST")

package com.windea.kotlin.utils

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
	
	/**
	 * 从指定的文件路径 [path] 读取yaml数据，返回一个映射。
	 */
	@Throws(Exception::class)
	fun fromFile(path: String): Map<String, Any?> {
		return fromFile(path, Map::class.java) as Map<String, Any?>
	}
	
	/**
	 * 从指定的文件路径 [path] 读取yaml数据，返回一个泛型对象。
	 */
	@Throws(Exception::class)
	fun <T> fromFile(path: String, type: Class<T>): T {
		val reader = FileReader(path)
		return yaml().loadAs(reader, type)
	}
	
	/**
	 * 从指定的文件路径 [path] 读取所有yaml数据，返回一个对象列表。
	 */
	@Throws(Exception::class)
	fun fromFileAll(path: String): List<Any> {
		val reader = FileReader(path)
		val resultList = ArrayList<Any>()
		for(elem in yaml().loadAll(reader)) {
			resultList.add(elem)
		}
		return resultList
	}
	
	/**
	 * 从指定的yaml字符串 [string] 读取yaml数据，返回一个映射。
	 */
	fun fromString(string: String): Map<String, Any?> {
		return fromString(string, Map::class.java) as Map<String, Any?>
	}
	
	/**
	 * 从指定的yaml字符串 [string] 读取yaml数据，返回一个泛型对象。
	 */
	fun <T> fromString(string: String, type: Class<T>): T {
		return yaml().loadAs(string, type)
	}
	
	/**
	 * 从指定的yaml字符串 [string] 读取所有yaml数据，返回一个对象列表。
	 */
	fun fromStringAll(string: String): List<Any> {
		val resultList = ArrayList<Any>()
		for(elem in yaml().loadAll(string)) {
			resultList.add(elem)
		}
		return resultList
	}
	
	/**
	 * 将指定的泛型对象 [data] 写入指定路径 [path] 的yaml文件。
	 */
	@Throws(Exception::class)
	fun <T> toFile(data: T, path: String) {
		val writer = FileWriter(path)
		yaml().dump(data, writer)
	}
	
	/**
	 * 将指定的泛型对象列表 [dataList] 全部写入指定路径 [path] 的yaml文件。
	 */
	@Throws(Exception::class)
	fun <T> toFileAll(dataList: List<T>, path: String) {
		val writer = FileWriter(path)
		yaml().dumpAll(dataList.iterator(), writer)
	}
	
	/**
	 * 将指定的泛型对象 [data] 写入yaml字符串，然后返回。
	 */
	fun <T> toString(data: T): String {
		return yaml().dump(data)
	}
	
	/**
	 * 将指定的泛型对象列表 [dataList] 全部写入yaml字符串，然后返回。
	 */
	fun <T> toStringAll(dataList: List<T>): String {
		return yaml().dumpAll(dataList.iterator())
	}
	
	
	private fun yaml(): Yaml {
		return Yaml(constructor, representer, dumperOptions, loaderOptions)
	}
}
