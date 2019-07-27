package com.windea.commons.kotlin.extension

import com.windea.commons.kotlin.enums.*
import java.io.*

/**得到可序列化对象的属性名数组。*/
val <T : Serializable> T.propertyNames: Array<String>
	get() {
		return this::class.java.getterMap.keys.toTypedArray<String>()
	}

/**得到可序列化对象的属性名-属性值映射。*/
val <T : Serializable> T.propertyMap: Map<String, Any?>
	get() {
		return this::class.java.getterMap.mapValues { (_, v1) -> v1.invoke(this) }.filterKeys { it != "class" }
	}


/**序列化可序列化对象，返回序列化后的指定格式[dataFormat]的字符串。*/
fun <T : Serializable> T.serialize(dataFormat: DataFormat): String {
	return dataFormat.loader.toString(this)
}

/**反序列化指定格式[dataFormat]的字符串，返回反序列化后的可序列化对象。*/
inline fun <reified T : Serializable> String.deserialize(dataFormat: DataFormat): T {
	return dataFormat.loader.fromString(this, T::class.java)
}

/**重新序列化指定格式[dataFormat]的字符串，返回指定格式[toDataFormat]的字符串。*/
fun String.reserialize(dataFormat: DataFormat, toDataFormat: DataFormat): String {
	return toDataFormat.loader.toString(dataFormat.loader.fromString(this, Any::class.java))
}
