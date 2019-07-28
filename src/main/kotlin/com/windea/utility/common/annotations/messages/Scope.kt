package com.windea.utility.common.annotations.messages

import kotlin.reflect.full.*

/**作用域的注解。*/
@MustBeDocumented
annotation class Scope(
	val text: String = "Default"
)


/**得到目标的作用域。*/
fun Any.annotatedScope(): String? {
	return this::class.findAnnotation<Scope>()?.text
}
