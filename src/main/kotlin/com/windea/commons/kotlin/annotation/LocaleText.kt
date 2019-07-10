package com.windea.commons.kotlin.annotation

/**本地化文本的注解。*/
@MustBeDocumented
@Repeatable
annotation class LocaleText(
	val value: String,
	val locale: String = "Chs"
)


/**得到目标的本地化文本。*/
fun Any.localeText(locale: String = "Chs"): String {
	return this::class.annotations.filterIsInstance<LocaleText>().firstOrNull { it.locale == locale }?.value ?: ""
}
