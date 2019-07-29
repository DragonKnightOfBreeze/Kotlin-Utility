package com.windea.utility.common.annotations.marks

import java.lang.annotation.*

/**不可用的项的注解。*/
@MustBeDocumented
@Inherited
annotation class NotUsable(
	val condition: String = "All condition."
)
