package com.windea.commons.kotlin.annotation

/**Markdown文本的注解。*/
@MustBeDocumented
annotation class MarkdownText(
	val value: String,
	val useExtendSyntax: Boolean = false,
	val useCriticMarkup: Boolean = false
)
