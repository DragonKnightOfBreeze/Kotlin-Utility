package com.windea.utility.common.annotations.intelligence

/**可被添加的项的注解。*/
@MustBeDocumented
@Repeatable
annotation class AppendedValue(
	val valueExpression: String,
	val message: String = "Appended value."
)
