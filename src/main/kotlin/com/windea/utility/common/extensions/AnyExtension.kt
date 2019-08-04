package com.windea.commons.kotlin.extension

import com.windea.utility.common.annotations.marks.*
import com.windea.utility.common.enums.*
import kotlin.reflect.full.*

/**序列化当前对象。*/
@NotSure("考虑使用扩展库`kotlinx-serialization`，但是缺少具体的对于yaml、xml等格式的实现")
fun Any.serialize(dataType: DataType): String {
	return dataType.loader.toString(this)
}


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
