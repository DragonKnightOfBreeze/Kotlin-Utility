package com.windea.utility.common.annotations.messages

import com.windea.utility.common.extensions.*

/**本地化名字的注解。*/
@MustBeDocumented
@Repeatable
annotation class Name(
	val text: String,
	val locale: String = "Chs"
)


/**得到目标的本地化名字。*/
fun Any.annotatedName(locale: String = "Chs"): String? {
	return this::class.findAnnotation<Name> { it.locale == locale }?.text
}
