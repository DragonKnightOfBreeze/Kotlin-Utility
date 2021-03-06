package com.windea.utility.common.annotations.intelligence

import kotlin.annotation.AnnotationTarget.*

/**可被添加的项的注解。*/
@MustBeDocumented
@Repeatable
@Target(PROPERTY, FIELD, LOCAL_VARIABLE)
annotation class AppendedValue(
	val valueExpression: String,
	val message: String = "Appended value."
)
