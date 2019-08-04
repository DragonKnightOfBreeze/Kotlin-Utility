package com.windea.utility.common.annotations.intelligence

/**按条件进行项的替换的注解。*/
@MustBeDocumented
@Repeatable
annotation class ConditionalReplace(
	val valueExpression: String,
	val condition: String = "All condition."
)
