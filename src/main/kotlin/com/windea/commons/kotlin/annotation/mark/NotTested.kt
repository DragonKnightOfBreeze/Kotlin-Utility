package com.windea.commons.kotlin.annotation.mark

import java.lang.annotation.*

/**未测试/未通过测试的项的注解。*/
@MustBeDocumented
@Inherited
annotation class NotTested(
	val condition: String = "Not tested."
)
