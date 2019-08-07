package com.windea.commons.kotlin.extension

import com.windea.utility.common.annotations.marks.*
import com.windea.utility.common.enums.*
import kotlin.reflect.full.*

/**取在指定范围内的夹值。*/
infix fun <T : Comparable<T>> T.clamp(range: ClosedRange<T>) = this.coerceIn(range)

/**从二元素元组构造三元素元组。*/
infix fun <A, B, C> Pair<A, B>.with(third: C) = Triple(this.first, this.second, third)


/**抛出一个[IllegalArgumentException]，带有懒加载的信息。*/
inline fun require(lazyMessage: () -> Any) = require(false, lazyMessage)

/**抛出一个[IllegalStateException]，带有懒加载的信息。*/
inline fun check(lazyMessage: () -> Any) = check(false, lazyMessage)


/**序列化当前对象。*/
fun Any.serialize(dataType: DataType): String {
	return dataType.loader.toString(this)
}


/**将当前对象转化为对应的属性名-属性值映射。*/
@NotSuitable("需要得到扩展属性信息时")
fun Any.toPropertyMap(): Map<String, Any?> {
	return this::class.memberProperties.associate { it.name to it.call(this) }
}

/**将当前对象转化为对应的属性名-属性值映射。其中的对象属性将会被递归转化。*/
@NotSuitable("需要得到扩展属性信息时")
fun Any.toDeepPropertyMap(): Map<String, Any?> {
	return this.toPropertyMap().mapValues {
		if(it !is Array<*> && it !is Iterable<*> && it !is Map<*, *>) {
			it.toDeepPropertyMap()
		} else {
			it
		}
	}
}
