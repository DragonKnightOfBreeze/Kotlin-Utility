package com.windea.commons.kotlin.annotation

/**
 * Markdown文本的注解。
 */
@Repeatable
annotation class MarkdownText(
	val value: String,
	val useExtendSyntax: Boolean = false,
	val useCriticMarkup: Boolean = false
)

fun Any.markdownText(index: Int = 0): String {
	return this::class.annotations.filterIsInstance<MarkdownText>()[index].value
}
