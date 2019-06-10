package com.windea.commons.kotlin.utils

/**
 * Json文件和Yaml文件的工具类。用于相互之间的转换。
 */
object JsonYamlUtils {
	/**
	 * 将指定路径 [path] 的json文件转化为相同文件名的yaml文件。
	 */
	@Throws(Exception::class)
	fun jsonToYamlFile(path: String) {
		jsonToYamlFile(path, PathUtils.changeFileExt(path, ".yml"))
	}
	
	/**
	 * 将指定路径 [jsonPath] 的json文件转化为指定路径 [yamlPath] 的yaml文件。
	 */
	@Throws(Exception::class)
	fun jsonToYamlFile(jsonPath: String, yamlPath: String) {
		val data = JsonUtils.fromFile(jsonPath)
		YamlUtils.toFile(data, yamlPath)
	}
	
	/**
	 * 将指定路径 [path] 的yaml文件转化为相同文件名的json文件。
	 */
	@Throws(Exception::class)
	fun yamlToJsonFile(path: String) {
		yamlToJsonFile(path, PathUtils.changeFileExt(path, ".json"))
	}
	
	/**
	 * 将指定路径 [yamlPath] 的yaml文件转化为指定路径 [jsonPath] 的json文件。
	 */
	@Throws(Exception::class)
	fun yamlToJsonFile(yamlPath: String, jsonPath: String) {
		val data = YamlUtils.fromFile(yamlPath)
		JsonUtils.toFile(data, jsonPath)
	}
}
