package com.windea.commons.kotlin.annotation

import java.lang.annotation.Inherited

/**
 * （在某些情况下）已通过测试的代码的注解。
 */
@MustBeDocumented
@Inherited
annotation class Tested(
	val value: String = "Tested."
)
