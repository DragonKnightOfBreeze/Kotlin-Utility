package com.windea.commons.kotlin.annotation

/**本地化文本的注解。*/
@MustBeDocumented
@Repeatable
annotation class LocaleText(
	val value: String,
	val locale: String = "Chs"
)



