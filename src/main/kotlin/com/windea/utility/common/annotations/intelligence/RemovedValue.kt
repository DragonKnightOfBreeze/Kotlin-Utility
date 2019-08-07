package com.windea.utility.common.annotations.intelligence

/**可被移除的项的注解。*/
@MustBeDocumented
@Repeatable
annotation class RemovedValue(
	val valueExpression: String,
	val message: String = "Removed value."
)
