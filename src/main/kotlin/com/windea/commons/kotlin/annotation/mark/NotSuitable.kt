package com.windea.commons.kotlin.annotation.mark

import java.lang.annotation.*

/**不适用的项的注解。*/
@MustBeDocumented
@Inherited
annotation class NotSuitable(
	val condition: String = "Not suitable."
)
