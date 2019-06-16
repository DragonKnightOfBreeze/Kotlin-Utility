package com.windea.commons.kotlin.annotation

/**
 * 未测试或未通过测试的代码的注解。
 */
@MustBeDocumented
annotation class NotTested(
	val message: String = "not tested"
)
