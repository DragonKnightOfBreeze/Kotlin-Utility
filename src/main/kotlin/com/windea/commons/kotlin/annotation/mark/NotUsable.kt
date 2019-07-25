package com.windea.commons.kotlin.annotation.mark

import java.lang.annotation.*

/**不可用的项的注解。*/
@MustBeDocumented
@Inherited
annotation class NotUsable(
	val condition: String = "Not usable."
)
