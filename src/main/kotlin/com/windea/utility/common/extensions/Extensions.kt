@file:Suppress("NOTHING_TO_INLINE")

package com.windea.utility.common.extensions

import kotlin.contracts.*
import kotlin.reflect.*

/**取在指定范围内的夹值。*/
infix fun <T : Comparable<T>> T.clamp(range: ClosedRange<T>): T {
	return this.coerceIn(range)
}

/**从二元素元组构造三元素元组。*/
infix fun <A, B, C> Pair<A, B>.with(third: C): Triple<A, B, C> {
	return Triple(this.first, this.second, third)
}


/**抛出一个[IllegalArgumentException]，带有懒加载的信息。*/
inline fun require(lazyMessage: () -> Any) = require(false, lazyMessage)

/**抛出一个[IllegalStateException]，带有懒加载的信息。*/
inline fun check(lazyMessage: () -> Any) = check(false, lazyMessage)

/**如果判定失败，则抛出一个[UnsupportedOperationException]。*/
@ExperimentalContracts
inline fun reject(value: Boolean) {
	contract {
		returns() implies value
	}
	reject(value) { "Unsupported operation." }
}

/**如果判定失败，则抛出一个[UnsupportedOperationException]，带有懒加载的信息。*/
@ExperimentalContracts
inline fun reject(value: Boolean, lazyMessage: () -> Any) {
	contract {
		returns() implies value
	}
	if(!value) {
		val message = lazyMessage()
		throw UnsupportedOperationException(message.toString())
	}
}

/**抛出一个[UnsupportedOperationException]，带有懒加载的信息。*/
@ExperimentalContracts
inline fun reject(lazyMessage: () -> Any) = reject(false, lazyMessage)


/**得到指定类型的名字。*/
inline fun <reified T> nameOf(): String? = T::class.simpleName

/**得到指定项的名字。适用于：类型、属性引用、方法引用、实例。不适用于：类型参数，参数，局部变量。*/
inline fun nameOf(target: Any?): String? {
	return when {
		target == null -> null
		target is KClass<*> -> target.simpleName
		target is KCallable<*> -> target.name
		target is KParameter -> target.name //无法直接通过方法的引用得到参数
		//无法得到局部变量的任何信息
		else -> target::class.simpleName
	}
}
