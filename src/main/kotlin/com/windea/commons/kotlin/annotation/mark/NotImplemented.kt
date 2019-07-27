package com.windea.commons.kotlin.annotation.mark

import java.lang.annotation.*

/**
 * 未实现的项的注解。
 */
@MustBeDocumented
@Inherited
annotation class NotImplemented(
	val condition: String = "Not implemented."
)
