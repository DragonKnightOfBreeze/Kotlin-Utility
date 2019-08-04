package com.windea.utility.common.annotations.intelligence

/**按条件进行项的移除的注解。*/
@MustBeDocumented
@Repeatable
annotation class ConditionalRemove(
	val valueExpression: String,
	val condition: String = "All condition."
)
