package com.windea.commons.kotlin.annotation.mark

import java.lang.annotation.*

/**未确定的项的注解。*/
@MustBeDocumented
@Inherited
annotation class NotSure(
	val condition: String = "Not sure."
)
