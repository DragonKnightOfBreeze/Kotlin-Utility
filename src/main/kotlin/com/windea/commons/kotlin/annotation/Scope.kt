package com.windea.commons.kotlin.annotation

import kotlin.reflect.full.*

/**作用域的注解。*/
@MustBeDocumented
annotation class Scope(
	val value: String = "Default"
)


/**得到目标的作用域。*/
fun Any.scope(): String? {
	return this::class.findAnnotation<Scope>()?.value
}
