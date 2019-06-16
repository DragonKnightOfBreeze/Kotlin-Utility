package com.windea.commons.kotlin.utils

/**
 * 数据类型转化器。
 */
object DataTypeConverter {
	fun jsonToYamlFile(jsonPath: String, yamlPath: String) {
		val data = JsonUtils.fromFile(jsonPath)
		YamlUtils.toFile(data, yamlPath)
	}
	
	fun jsonToYamlFile(path: String) {
		jsonToYamlFile(path, PathUtils.changeFileExt(path, ".yml"))
	}
	
	fun yamlToJsonFile(yamlPath: String, jsonPath: String) {
		val data = YamlUtils.fromFile(yamlPath)
		JsonUtils.toFile(data, jsonPath)
	}
	
	fun yamlToJsonFile(path: String) {
		yamlToJsonFile(path, PathUtils.changeFileExt(path, ".json"))
	}
}
