package com.windea.commons.kotlin.extension

import com.windea.commons.kotlin.annotation.*
import kotlin.reflect.*
import kotlin.reflect.full.*

/**得到可注解元素的指定类型[A]的满足指定预测[predicate]的首个注解。*/
inline fun <reified A : Annotation> KAnnotatedElement.findAnnotation(predicate: (A) -> Boolean): A? {
	return this.annotations.filterIsInstance<A>().firstOrNull(predicate)
}

/**得到可注解元素的指定类型的注解列表。*/
inline fun <reified A : Annotation> KAnnotatedElement.findAnnotations(): List<A> {
	return this.annotations.filterIsInstance<A>()
}

/**得到可注解元素的指定类型的满足指定预测[predicate]的注解列表。*/
inline fun <reified A : Annotation> KAnnotatedElement.findAnnotations(predicate: (A) -> Boolean): List<A> {
	return this.annotations.filterIsInstance<A>().filter(predicate)
}


/**得到目标的本地化文本。*/
fun Any.localeText(locale: String = "Chs"): String {
	return this::class.findAnnotations<LocaleText>().firstOrNull { it.locale == locale }?.value ?: ""
}

/**得到目标的Markdown文本。*/
fun Any.markdownText(): String {
	return this::class.findAnnotation<MarkdownText>()?.value ?: ""
}

/**得到目标的作用域。*/
fun Any.scope(): String {
	return this::class.findAnnotation<Scope>()?.value ?: ""
}
