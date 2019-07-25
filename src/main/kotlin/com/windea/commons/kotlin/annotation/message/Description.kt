package com.windea.commons.kotlin.annotation.message

import com.windea.commons.kotlin.extension.*
import org.intellij.lang.annotations.*

/**本地化描述的注解。*/
@MustBeDocumented
@Repeatable
annotation class Description(
	@Language("Markdown")
	val text: String,
	val locale: String = "Chs"
)


/**得到目标的本地化描述。*/
fun Any.annotatedDescription(locale: String = "Chs"): String? {
	return this::class.findAnnotation<Description> { it.locale == locale }?.text ?: this.annotatedSummary(locale)
}
