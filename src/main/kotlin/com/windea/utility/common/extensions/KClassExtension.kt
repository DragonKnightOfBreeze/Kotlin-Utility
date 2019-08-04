package com.windea.utility.common.extensions

import kotlin.reflect.*

/**得到可注解元素的指定类型的满足指定预测的首个注解。*/
inline fun <reified A : Annotation> KAnnotatedElement.findAnnotation(predicate: (A) -> Boolean): A? {
	return this.annotations.filterIsInstance<A>().firstOrNull(predicate)
}

/**得到可注解元素的指定类型的注解列表。*/
inline fun <reified A : Annotation> KAnnotatedElement.findAnnotations(): List<A> {
	return this.annotations.filterIsInstance<A>()
}

/**得到可注解元素的指定类型的满足指定预测的注解列表。*/
inline fun <reified A : Annotation> KAnnotatedElement.findAnnotations(predicate: (A) -> Boolean): List<A> {
	return this.annotations.filterIsInstance<A>().filter(predicate)
}
