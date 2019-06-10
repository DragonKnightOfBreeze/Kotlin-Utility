package com.windea.commons.kotlin.annotation

/**
 * Markdown附加注释的注解。
 */
@Repeatable
annotation class Markdown(
	val text: String,
	val useExtend: Boolean = false,
	val useCriticMarkup: Boolean = false
)
