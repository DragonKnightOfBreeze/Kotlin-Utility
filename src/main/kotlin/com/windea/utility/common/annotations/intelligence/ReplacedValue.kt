package com.windea.utility.common.annotations.intelligence

/**可被替换的项的注解。*/
@MustBeDocumented
@Repeatable
annotation class ReplacedValue(
	val valueExpression: String,
	val message: String = "Replaced value."
)
