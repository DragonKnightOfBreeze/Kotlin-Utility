package com.windea.utility.common.enums

import com.windea.utility.common.loaders.*

/**数据类型。*/
enum class DataType(
	val extension: String,
	val loader: DataLoader
) {
	Json("json", JsonLoader.instance),
	Yaml("yml", YamlLoader.instance),
	Xml("xml", XmlLoader.instance),
	Properties("properties", PropertiesLoader.instance)
}
