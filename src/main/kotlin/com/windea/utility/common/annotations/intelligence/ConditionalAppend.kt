package com.windea.utility.common.annotations.intelligence

/**按条件进行项的添加的注解。*/
@MustBeDocumented
@Repeatable
annotation class ConditionalAppend(
	val valueExpression: String,
	val condition: String = "All condition."
)
