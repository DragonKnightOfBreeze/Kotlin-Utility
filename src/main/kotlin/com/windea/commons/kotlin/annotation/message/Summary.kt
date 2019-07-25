package com.windea.commons.kotlin.annotation.message

import com.windea.commons.kotlin.extension.*
import org.intellij.lang.annotations.*

/**本地化概述的注解。*/
@MustBeDocumented
@Repeatable
annotation class Summary(
	@Language("Markdown")
	val text: String,
	val locale: String = "Chs"
)


/**得到目标的本地化概述。*/
fun Any.annotatedSummary(locale: String = "Chs"): String? {
	return this::class.findAnnotation<Summary> { it.locale == locale }?.text
}
