package com.windea.kotlin.annotation

/**
 * 已通过测试的代码的注解。
 */
@MustBeDocumented
annotation class Tested(
	val message: String = "tested"
)
