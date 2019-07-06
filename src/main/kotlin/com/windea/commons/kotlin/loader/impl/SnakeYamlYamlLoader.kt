@file:Suppress("UNCHECKED_CAST")

package com.windea.commons.kotlin.loader.impl

import com.windea.commons.kotlin.loader.YamlLoader
import org.yaml.snakeyaml.DumperOptions
import org.yaml.snakeyaml.LoaderOptions
import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor.Constructor
import org.yaml.snakeyaml.representer.Representer
import java.io.FileReader
import java.io.FileWriter
import java.util.*

class SnakeYamlYamlLoader : YamlLoader {
	private val constructor = Constructor()
	private val representer = Representer()
	private val loaderOptions = LoaderOptions()
	private val dumperOptions = DumperOptions()
	
	init {
		loaderOptions.isAllowDuplicateKeys = false
		dumperOptions.isAllowReadOnlyProperties = true
		dumperOptions.width = 120
		dumperOptions.lineBreak = DumperOptions.LineBreak.WIN
		dumperOptions.timeZone = TimeZone.getTimeZone("GMT-8:00")
		dumperOptions.isPrettyFlow = true
	}
	
	
	private fun yaml(): Yaml {
		return Yaml(constructor, representer, dumperOptions, loaderOptions)
	}
	
	fun configureLoaderOptions(handler: (options: LoaderOptions) -> Unit): SnakeYamlYamlLoader {
		handler.invoke(loaderOptions)
		return this
	}
	
	fun configureDumperOptions(handler: (options: DumperOptions) -> Unit): SnakeYamlYamlLoader {
		handler.invoke(dumperOptions)
		return this
	}
	
	fun addTags(tagMap: Map<String, String>): SnakeYamlYamlLoader {
		dumperOptions.tags.putAll(tagMap)
		return this
	}
	
	override fun <T : Any> fromFile(path: String, type: Class<T>): T {
		val reader = FileReader(path)
		return yaml().loadAs(reader, type)
	}
	
	override fun fromFile(path: String): Map<String, Any?> {
		return fromFile(path, Map::class.java) as Map<String, Any?>
	}
	
	override fun fromFileAll(path: String): List<Any> {
		val reader = FileReader(path)
		val resultList = ArrayList<Any>()
		for(elem in yaml().loadAll(reader)) {
			resultList.add(elem)
		}
		return resultList
	}
	
	override fun <T : Any> fromString(string: String, type: Class<T>): T {
		return yaml().loadAs(string, type)
	}
	
	override fun fromString(string: String): Map<String, Any?> {
		return fromString(string, Map::class.java) as Map<String, Any?>
	}
	
	override fun fromStringAll(string: String): List<Any> {
		val resultList = ArrayList<Any>()
		for(elem in yaml().loadAll(string)) {
			resultList.add(elem)
		}
		return resultList
	}
	
	override fun <T : Any> toFile(data: T, path: String) {
		val writer = FileWriter(path)
		yaml().dump(data, writer)
	}
	
	override fun <T : Any> toFileAll(dataList: List<T>, path: String) {
		val writer = FileWriter(path)
		yaml().dumpAll(dataList.iterator(), writer)
	}
	
	override fun <T : Any> toString(data: T): String {
		return yaml().dump(data)
	}
	
	override fun <T : Any> toStringAll(dataList: List<T>): String {
		return yaml().dumpAll(dataList.iterator())
	}
}
