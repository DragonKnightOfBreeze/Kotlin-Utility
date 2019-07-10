package com.windea.commons.kotlin.annotation

import kotlin.reflect.full.*

/**Markdown文本的注解。*/
@MustBeDocumented
annotation class MarkdownText(
	val value: String,
	val useExtendSyntax: Boolean = false,
	val useCriticMarkup: Boolean = false
)


/**得到目标的Markdown文本。*/
fun Any.markdownText(): String {
	return this::class.findAnnotation<MarkdownText>()?.value ?: "No markdown text."
}
