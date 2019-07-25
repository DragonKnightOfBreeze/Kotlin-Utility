@file:Suppress("UNCHECKED_CAST")

package com.windea.commons.kotlin.extension

import com.windea.commons.kotlin.annotation.*

operator fun <T> Iterable<T>.times(n: Int): List<T> {
	return mutableListOf<T>().also { list -> repeat(n) { list += this } }
}

operator fun <T> Iterable<T>.div(n: Int): List<List<T>> {
	return this.chunked(n)
}


/**判断当前数组是否以指定元素开始。*/
infix fun <T> Array<T>.startsWith(element: T): Boolean {
	return this.firstOrNull() == element
}

/**判断当前数组是否以任意指定元素开始。*/
infix fun <T> Array<T>.startsWith(elements: Array<T>): Boolean {
	return this.firstOrNull() in elements
}

/**判断当前数组是否以指定元素结束。*/
infix fun <T> Array<T>.endsWith(element: T): Boolean {
	return this.firstOrNull() == element
}

/**判断当前数组是否以任意指定元素结束。*/
infix fun <T> Array<T>.endsWith(elements: Array<T>): Boolean {
	return this.firstOrNull() in elements
}

/**判断当前集合是否以指定元素开始。*/
infix fun <T> Iterable<T>.startsWith(element: T): Boolean {
	return this.firstOrNull() == element
}

/**判断当前集合是否以任意指定元素开始。*/
infix fun <T> Iterable<T>.startsWith(elements: Array<T>): Boolean {
	return this.firstOrNull() in elements
}

/**判断当前集合是否以指定元素结束。*/
infix fun <T> Iterable<T>.endsWith(element: T): Boolean {
	return this.firstOrNull() == element
}

/**判断当前可迭代对象是否以任意指定元素结束。*/
infix fun <T> Iterable<T>.endsWith(elements: Array<T>): Boolean {
	return this.firstOrNull() in elements
}


/**得到指定索引的元素，发生异常则得到默认值。*/
fun <T> Array<T>.getOrDefault(index: Int, defaultValue: T): T {
	return this.getOrElse(index) { defaultValue }
}

/**得到指定索引的元素，发生异常则得到默认值。*/
fun <E> List<E>.getOrDefault(index: Int, defaultValue: E): E {
	return this.getOrElse(index) { defaultValue }
}


/**
 * 根据指定路径[path]查询当前数组，返回匹配的元素列表。
 *
 * @see privateQueryValue
 */
fun <T> Array<T>.query(path: String) = privateQueryValue(this.toList(), path)

/**
 * 根据指定路径[path]查询当前集合，返回匹配的元素列表。
 *
 * @see privateQueryValue
 */
fun <E> Iterable<E>.query(path: String) = privateQueryValue(this.toList(), path)

/**
 * 根据指定路径[path]查询当前映射，返回匹配的值列表。
 *
 * @see privateQueryValue
 */
fun <K, V> Map<K, V>.queryValue(path: String) = privateQueryValue(this, path)

/**
 * * 示例： `#/{Category}/{Name}/Name`。
 *
 * 允许的子路径格式：
 * * `[]` 表示一个列表。
 * * `1..10` 表示一个所应在指定范围内的列表。包含上下限。
 * * `1` 表示一个列表的索引。
 * * `{}` 表示一个对象/映射。
 * * `{Category}` 表示一个注为指定占位符的对象/映射。
 * * `re:Name.*` 表示一个属性/键匹配指定正则的对象/映射。
 * * `Name` 表示一个对象/映射的属性/键。
 */
@NotTested("某些特殊情况？")
private fun privateQueryValue(collection: Any?, path: String): List<Any?> {
	val fixedPath = path.trim().removePrefix("#").removePrefix("/")
	val subPaths = fixedPath.split("/")
	var valueList = listOf(collection)
	
	for(subPath in subPaths) {
		valueList = when {
			//如果子路径表示一个列表，例如："[]"
			subPath == "[]" -> {
				valueList.flatMap { it as List<Any?> }
			}
			//如果子路径表示一个范围，例如："1..10"
			subPath matches Regex("\\d+\\.\\.\\d+") -> {
				val (fromIndex, toIndex) = subPath.split("..").map { it.toInt() }
				valueList.flatMap { (it as List<Any?>) }.subList(fromIndex, toIndex + 1)
			}
			//如果子路径表示一个列表索引，例如："1"
			subPath matches Regex("\\d+") -> {
				val index = subPath.toInt()
				valueList.map { (it as List<Any?>)[index] }
			}
			//如果子路径表示一个对象，例如："{}"
			subPath == "{}" -> {
				valueList.flatMap { (it as Map<String, Any>).values }
			}
			//如果子路径表示一个占位符，例如："{Category}"
			subPath matches Regex("\\{.+}") -> {
				valueList.flatMap { (it as Map<String, Any?>).values }
			}
			//如果子路径表示一个正则表达式，例如："/Name.*/"
			subPath matches Regex("/.*/") -> {
				val pattern = subPath.removeSurrounding("/")
				valueList.flatMap { (it as Map<String, Any?>).filterKeys { k -> k matches Regex(pattern) }.values }
			}
			//如果是其他情况，例如："Name"
			else -> {
				valueList.map { (it as Map<String, Any?>)[subPath] }
			}
		}
	}
	return valueList
}


/**向下递归平滑映射当前数组，返回路径-值映射。*/
fun <T> Array<T>.deepFlatMap() = privateDeepFlatMap(this.toIndexedMap(), mutableListOf())

/**向下递归平滑映射当前集合，返回路径-值映射。*/
fun <E> Iterable<E>.deepFlatMap() = privateDeepFlatMap(this.toIndexedMap(), mutableListOf())

/**向下递归平滑映射当前映射，返回路径-值映射。*/
fun <K, V> Map<K, V>.deepFlatMap() = privateDeepFlatMap(this as Map<String, Any?>, mutableListOf())

@NotTested("某些特殊情况？")
private fun privateDeepFlatMap(map: Map<String, Any?>, prePaths: MutableList<String>): Map<String, Any?> {
	return map.flatMap { (key, value) ->
		prePaths += key
		when(value) {
			is Map<*, *> -> privateDeepFlatMap(value as Map<String, Any?>, prePaths).toList()
			is List<*> -> privateDeepFlatMap(value.toIndexedMap(), prePaths).toList()
			else -> {
				val fullPath = prePaths.joinToString(".").replace(Regex("\\.(\\d*)\\."), "[$1].")
				listOf(Pair(fullPath, value))
			}
		}
	}.toMap()
}


/**移除指定范围内的元素。*/
fun <E> MutableList<E>.removeAllAt(indices: IntRange) {
	for(index in indices.reversed()) this.removeAt(index)
}


/**将指定索引的元素插入到另一索引处。后者为移动前的索引，而非移动后的索引。*/
fun <E> MutableList<E>.move(fromIndices: Int, toIndex: Int) {
	val element = this[fromIndices]
	this.add(toIndex, element)
	this.removeAt(fromIndices)
}

/**将指定索引范围内的元素插入到以另一索引为起点处。后者为移动前的索引，而非移动后的索引。*/
fun <E> MutableList<E>.moveAll(fromIndices: IntRange, toIndex: Int) {
	val elements = this.slice(fromIndices)
	this.addAll(toIndex, elements)
	this.removeAllAt(fromIndices)
}


/**将当前数组转化成以键为值的映射。*/
fun <T> Array<T>.toIndexedMap(): Map<String, T> {
	return this.withIndex().associate { (i, e) -> Pair(i.toString(), e) }
}

/**将当前集合转化成以键为值的映射。*/
fun <T> Iterable<T>.toIndexedMap(): Map<String, T> {
	return this.withIndex().associate { (i, e) -> Pair(i.toString(), e) }
}


/**将当前映射转化为指定类型[T]的对象。可指定是否递归转化[recursive]，默认为true。*/
fun <T> Map<String, Any?>.toObject(type: Class<T>, recursive: Boolean = true) = privateToObject(this, type, recursive)

@NotTested("不存在无参构造方法，转化需要转化元素的数组时，其他特殊情况？")
private fun <T> privateToObject(map: Map<String, Any?>, type: Class<T>, recursive: Boolean = true): T {
	val newObject = type.getConstructor().newInstance()
	val propertyMap = type.setterMap
	for((propertyName, setMethod) in propertyMap) {
		if(!propertyMap.containsKey(propertyName)) {
			continue
		}
		val propertyValue = map[propertyName]
		try {
			val propertyType = type.getDeclaredField(propertyName).type
			val fixedPropertyValue = convertProperty(propertyType, propertyValue, recursive)
			setMethod.invoke(newObject, fixedPropertyValue)
		} catch(e: Exception) {
			println("[WARN] Property type mismatch. Class: ${type.name}, Name: $propertyName, Value: $propertyValue}.")
		}
	}
	return newObject
}

private fun convertProperty(propertyType: Class<*>, propertyValue: Any?, recursive: Boolean = false): Any? {
	return when {
		propertyType.isPrimitive || propertyType.isCharSequence -> propertyValue
		propertyType.isEnum -> propertyValue.toString().toEnumConst(propertyType)
		//使用高阶函数后，无法直接得到运行时泛型
		propertyType.isArray -> (propertyValue as Array<*>)
		propertyType.isList -> (propertyValue as List<*>).map {
			it?.let { convertProperty(it.javaClass, it, recursive) }
		}
		propertyType.isSet -> (propertyValue as Set<*>).map {
			it?.let { convertProperty(it.javaClass, it, recursive) }
		}.toSet()
		propertyType.isMap -> (propertyValue as Map<*, *>).mapValues { (_, v) ->
			v?.let { convertProperty(v.javaClass, v, recursive) }
		}
		propertyType.isSerializable && recursive -> {
			privateToObject((propertyValue as Map<String, Any?>), propertyType)
		}
		else -> null
	}
}


/**得到指定索引的值，如果出错，则返回空字符串。*/
fun Array<String>.getOrEmpty(index: Int) = this.getOrElse(index) { "" }

/**得到指定索引的值，如果出错，则返回空字符串。*/
fun List<String>.getOrEmpty(index: Int) = this.getOrElse(index) { "" }


/**过滤空字符串。*/
fun Array<CharSequence>.filterNotEmpty() = this.filter { it.isNotEmpty() }

/**过滤空白字符串。*/
fun Array<CharSequence>.filterNotBlank() = this.filter { it.isNotEmpty() }

/**过滤空字符串。*/
fun Iterable<CharSequence>.filterNotEmpty() = this.filter { it.isNotEmpty() }

/**过滤空白字符串。*/
fun Iterable<CharSequence>.filterNotBlank() = this.filter { it.isNotEmpty() }


/**映射当前带索引值集合的索引，返回带有新的索引的带索引值集合。*/
inline fun <T> Iterable<IndexedValue<T>>.mapIndices(transform: (Int) -> Int): Iterable<IndexedValue<T>> {
	return this.map { (index, value) -> IndexedValue(transform(index), value) }
}

/**映射当前带索引值集合的值，返回带有新的值的带索引值集合。*/
inline fun <T> Iterable<IndexedValue<T>>.mapValues(transform: (T) -> T): Iterable<IndexedValue<T>> {
	return this.map { (index, value) -> IndexedValue(index, transform(value)) }
}
