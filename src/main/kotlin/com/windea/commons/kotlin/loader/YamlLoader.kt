package com.windea.commons.kotlin.loader

import com.windea.commons.kotlin.loader.impl.SnakeYamlYamlLoader

/**
 * Yaml数据读取器的接口。
 */
interface YamlLoader : DataLoader {
	fun fromFileAll(path: String): List<Any>
	
	fun fromStringAll(string: String): List<Any>
	
	fun <T : Any> toFileAll(dataList: List<T>, path: String)
	
	fun <T : Any> toStringAll(dataList: List<T>): String
	
	
	companion object {
		val instance = SnakeYamlYamlLoader()
	}
}
