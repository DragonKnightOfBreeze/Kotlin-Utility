package com.windea.commons.kotlin.annotation.mark

import java.lang.annotation.*

/**未包含的项的注解。*/
@MustBeDocumented
@Inherited
annotation class NotIncluded(
	val condition: String = "Not included."
)
