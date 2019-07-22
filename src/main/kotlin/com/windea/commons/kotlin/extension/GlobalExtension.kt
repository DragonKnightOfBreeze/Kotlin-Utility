package com.windea.commons.kotlin.extension

/**接受一个集合，对于集合中的每个元素，链式执行一段以其为参数的代码，返回累积的结果。*/
inline fun <T, E> T.chaining(collection: Iterable<E>, block: T.(E) -> T): T {
	var result = this
	for(item in collection) {
		result = result.block(item)
	}
	return result
}

/**接受一个集合，对于集合中的每个元素，链式执行一段以其为参数的代码，返回累积的结果。*/
inline fun <T, E> T.chaining(collection: Array<E>, block: T.(E) -> T): T {
	var result = this
	for(item in collection) {
		result = result.block(item)
	}
	return result
}
