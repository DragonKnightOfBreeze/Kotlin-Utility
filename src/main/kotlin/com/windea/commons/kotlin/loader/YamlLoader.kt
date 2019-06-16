package com.windea.commons.kotlin.loader

import com.windea.commons.kotlin.loader.impl.SnakeYamlYamlLoader

interface YamlLoader : DataLoader {
	fun fromFileAll(path: String): List<Any>
	
	fun fromStringAll(string: String): List<Any>
	
	fun <T> toFileAll(dataList: List<T>, path: String)
	
	fun <T> toStringAll(dataList: List<T>): String
	
	
	companion object {
		val instance = SnakeYamlYamlLoader()
	}
}
