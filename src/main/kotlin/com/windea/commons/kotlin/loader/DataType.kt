package com.windea.commons.kotlin.loader

enum class DataType constructor(
	val fileExt: String,
	val loader: DataLoader
) {
	Json(".json", JsonLoader.instance),
	Yaml(".yml", YamlLoader.instance),
	Xml(".xml", XmlLoader.instance),
	Properties(".properties", PropertiesLoader.instance);
}
