package com.windea.utility.common.annotations.intelligence

/**被省略的项的注解。*/
@MustBeDocumented
annotation class OmitValue(
	val valueExpression: String,
	val message: String = "Removed value."
)
