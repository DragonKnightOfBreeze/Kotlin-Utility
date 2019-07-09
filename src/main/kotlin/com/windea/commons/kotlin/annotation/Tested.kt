package com.windea.commons.kotlin.annotation

/**
 * （在某些情况下）已通过测试的代码的注解。
 */
@MustBeDocumented
annotation class Tested(
	val value: String = "Tested."
)
