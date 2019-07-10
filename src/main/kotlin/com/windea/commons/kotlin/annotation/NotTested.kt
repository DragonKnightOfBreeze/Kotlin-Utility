package com.windea.commons.kotlin.annotation

import java.lang.annotation.Inherited

/**
 * （在某些情况下）未测试/未通过测试的代码的注解。
 */
@MustBeDocumented
@Inherited
annotation class NotTested(
	val value: String = "Not tested."
)
