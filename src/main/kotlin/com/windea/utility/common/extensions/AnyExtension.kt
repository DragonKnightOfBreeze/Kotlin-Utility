package com.windea.commons.kotlin.extension

import kotlin.reflect.full.*

/**将当前对象转化为对应的属性名-属性值映射。*/
fun Any.toPropertyMap(): Map<String, Any?> {
	return this::class.memberProperties.associate { it.name to it.call(this) }
}

/**将当前对象转化为对应的属性名-属性值映射。其中的对象属性将会被递归转化。*/
fun Any.toDeepPropertyMap(): Map<String, Any?> {
	return this.toPropertyMap().mapValues {
		if(it !is Array<*> && it !is Iterable<*> && it !is Map<*, *>) {
			it.toDeepPropertyMap()
		} else {
			it
		}
	}
}
