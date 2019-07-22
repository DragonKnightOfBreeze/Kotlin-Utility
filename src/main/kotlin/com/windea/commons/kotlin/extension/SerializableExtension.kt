package com.windea.commons.kotlin.extension

import java.io.*

/**得到方法的属性名数组。*/
val <T : Serializable> T.propertyNames: Array<String>
	get() {
		return this::class.java.getterMap.keys.toTypedArray<String>()
	}

/**得到方法的属性名-属性值映射。*/
val <T : Serializable> T.propertyMap: Map<String, Any?>
	get() {
		return this::class.java.getterMap.mapValues { (_, v1) -> v1.invoke(this) }.filterKeys { it != "class" }
	}
