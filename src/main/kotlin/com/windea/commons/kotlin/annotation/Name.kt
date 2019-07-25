package com.windea.commons.kotlin.annotation

import com.windea.commons.kotlin.extension.*

/**本地化名字的注解。*/
@MustBeDocumented
@Repeatable
annotation class Name(
	val value: String,
	val locale: String = "Chs"
)


/**得到目标的本地化名字。*/
fun Any.annotatedName(locale: String = "Chs"): String? {
	return this::class.findAnnotation<Name> { it.locale == locale }?.value
}
