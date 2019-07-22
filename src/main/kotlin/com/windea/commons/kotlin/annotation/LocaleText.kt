package com.windea.commons.kotlin.annotation

import com.windea.commons.kotlin.extension.*

/**本地化文本的注解。*/
@MustBeDocumented
@Repeatable
annotation class LocaleText(
	val value: String,
	val locale: String = "Chs"
)


/**得到目标的本地化文本。*/
fun Any.localeText(locale: String = "Chs"): String? {
	return this::class.findAnnotation<LocaleText> { it.locale == locale }?.value
}
