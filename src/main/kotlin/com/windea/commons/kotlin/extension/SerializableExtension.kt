package com.windea.commons.kotlin.extension

import java.io.*

/**得到方法的属性名-属性值映射。*/
fun <T : Serializable> T.getPropertyMap(): Map<String, Any?> {
	return this::class.java.getGetterMap().mapValues { (_, v1) -> v1.invoke(this) }.filterKeys { it != "class" }
}
