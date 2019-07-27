package com.windea.commons.kotlin.enums

import com.windea.commons.kotlin.loader.*

/**数据格式。*/
enum class DataFormat(
	/**扩展名。*/
	val extension: String,
	/**对应的数据读取器。*/
	val loader: DataLoader
) {
	Json("json", JsonLoader.instance),
	Yaml("yml", YamlLoader.instance),
	Xml("xml", XmlLoader.instance),
	Properties("properties", PropertiesLoader.instance)
}
