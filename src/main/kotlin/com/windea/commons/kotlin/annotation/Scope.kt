package com.windea.commons.kotlin.annotation

import kotlin.reflect.full.findAnnotation

/**
 * 作用域的注解。
 */
@MustBeDocumented
annotation class Scope(
	val value: String = "Default"
)


fun Any.scope(): String {
	return this::class.findAnnotation<Scope>()?.value ?: "No scope."
}
