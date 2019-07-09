package com.windea.commons.kotlin.annotation

/**
 * （在某些情况下）未测试或未通过测试的代码的注解。
 */
@MustBeDocumented
annotation class NotTested(
	val value: String = "Not tested."
)
