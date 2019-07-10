package com.windea.commons.kotlin.extension

import com.windea.commons.kotlin.annotation.*
import java.io.*

/**将对象转化为映射。不递归转化。*/
@NotTested("递归转化")
fun <T : Serializable> T.toMap(): Map<String, Any?> {
	return this.javaClass.getGetterMap().mapValues { (_, v1) -> v1.invoke(this) }.filterKeys { it != "class" }
}
