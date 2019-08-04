@file:Suppress("UNCHECKED_CAST")

package com.windea.utility.common.extensions

import com.windea.utility.common.annotations.marks.*
import com.windea.utility.common.enums.*

/**@see com.windea.utility.common.extensions.repeat*/
operator fun <T> Iterable<T>.times(n: Int) = this.repeat(n)

/**@see kotlin.collections.chunked*/
operator fun <T> Iterable<T>.div(n: Int) = this.chunked(n)


/**判断两个列表的元素是否全部相等。同时判断长度是否相等、顺序是否相同。*/
infix fun <T> List<T>.allEquals(other: List<T>): Boolean {
	return this.size == other.size && (this zip other).all { it.first == it.second }
}

/**判断两个列表的元素是否有任意一个相等。*/
infix fun <T> List<T>.anyEquals(other: List<T>): Boolean {
	return (this.isEmpty() && other.isEmpty()) || this.any { it in other }
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


/**重复当前集合中的元素到指定次数。*/
fun <T> Iterable<T>.repeat(n: Int): List<T> {
	require(n >= 0) { "Count 'n' must be non-negative, but was $n." }
	
	return mutableListOf<T>().also { list -> repeat(n) { list += this } }
}


/**移除指定范围内的元素。*/
fun <E> MutableList<E>.removeAllAt(indices: IntRange) {
	for(index in indices.reversed()) this.removeAt(index)
}


/**将指定索引的元素插入到另一索引处。后者为移动前的索引，而非移动后的索引。*/
fun <E> MutableList<E>.move(fromIndices: Int, toIndex: Int): E {
	val element = this[fromIndices]
	this.add(toIndex, element)
	return this.removeAt(fromIndices)
}

/**将指定索引范围内的元素插入到以另一索引为起点处。后者为移动前的索引，而非移动后的索引。*/
fun <E> MutableList<E>.moveAll(fromIndices: IntRange, toIndex: Int) {
	val elements = this.slice(fromIndices)
	this.addAll(toIndex, elements)
	this.removeAllAt(fromIndices)
}


/**根据路径得到当前数组中的元素。使用引用路径[PathCase.ReferencePath]。*/
fun <T> Array<T>.deepGet(path: String) =
	privateDeepGet(this.toIndexKeyMap(), path.splitByPathCase(PathCase.ReferencePath))

/**根据路径得到当前列表中的元素。使用引用路径[PathCase.ReferencePath]。*/
fun <E> List<E>.deepGet(path: String) =
	privateDeepGet(this.toIndexKeyMap(), path.splitByPathCase(PathCase.ReferencePath))

/**根据路径得到当前映射中的值。使用引用路径[PathCase.ReferencePath]。*/
fun <K, V> Map<K, V>.deepGet(path: String) =
	privateDeepGet(this.toStringKeyMap(), path.splitByPathCase(PathCase.ReferencePath))

@NotTested
private tailrec fun privateDeepGet(map: Map<String, Any?>, subPaths: List<String>): Any? {
	if(subPaths.size == 1) {
		return map[subPaths.first()]
	}
	val fixedDeepValue = when(val deepValue = map[subPaths.first()]) {
		is Map<*, *> -> deepValue.toStringKeyMap()
		is Array<*> -> deepValue.toIndexKeyMap()
		is Iterable<*> -> deepValue.toIndexKeyMap()
		else -> throw IllegalArgumentException("[ERROR] There is not a value related to this reference path.")
	}
	val fixedSubPaths = subPaths.drop(1)
	return privateDeepGet(fixedDeepValue, fixedSubPaths)
}


/**根据路径设置当前数组中的元素。使用引用路径[PathCase.ReferencePath]。*/
fun <T> Array<T>.deepSet(path: String, value: Any?) =
	privateDeepSet(this.toIndexKeyMap().toMutableMap(), path.splitByPathCase(PathCase.ReferencePath), value)

/**根据路径设置当前列表中的元素。使用引用路径[PathCase.ReferencePath]。*/
fun <E> MutableList<E>.deepSet(path: String, value: Any?) =
	privateDeepSet(this.toIndexKeyMap().toMutableMap(), path.splitByPathCase(PathCase.ReferencePath), value)

/**根据路径设置当前集合中的元素。使用引用路径[PathCase.ReferencePath]。*/
fun <K, V> MutableMap<K, V>.deepSet(path: String, value: Any?) =
	privateDeepSet(this.toStringKeyMap().toMutableMap(), path.splitByPathCase(PathCase.ReferencePath), value)

@NotTested
private tailrec fun privateDeepSet(map: MutableMap<String, Any?>, subPaths: List<String>, value: Any?) {
	if(subPaths.size == 1) {
		map[subPaths.first()] = value
		return
	}
	val fixedDeepValue = when(val deepValue = map[subPaths.first()]) {
		is Map<*, *> -> deepValue.toStringKeyMap().toMutableMap()
		is Array<*> -> deepValue.toIndexKeyMap().toMutableMap()
		is Iterable<*> -> deepValue.toIndexKeyMap().toMutableMap()
		else -> throw IllegalArgumentException("[ERROR] There is not a value related to this reference path.")
	}
	val fixedSubPaths = subPaths.drop(1)
	privateDeepSet(fixedDeepValue, fixedSubPaths, value)
}


/**递归平滑映射当前数组，返回路径-值映射。使用引用路径[PathCase.ReferencePath]。*/
fun <T> Array<T>.deepFlatMap() = privateDeepFlatMap(toIndexKeyMap(), mutableListOf())

/**递归平滑映射当前集合，返回路径-值映射。使用引用路径[PathCase.ReferencePath]。*/
fun <T> Iterable<T>.deepFlatMap() = privateDeepFlatMap(toIndexKeyMap(), mutableListOf())

/**递归平滑映射当前映射，返回路径-值映射。使用引用路径[PathCase.ReferencePath]。*/
fun <K, V> Map<K, V>.deepFlatMap() = privateDeepFlatMap(this.toStringKeyMap(), mutableListOf())

private fun privateDeepFlatMap(map: Map<String, Any?>, prePaths: MutableList<String>): Map<String, Any?> {
	return map.flatMap { (key, value) ->
		prePaths += key
		when(value) {
			is Array<*> -> privateDeepFlatMap(value.toIndexKeyMap(), prePaths).toList()
			is Iterable<*> -> privateDeepFlatMap(value.toIndexKeyMap(), prePaths).toList()
			is Map<*, *> -> privateDeepFlatMap(value.toStringKeyMap(), prePaths).toList()
			else -> listOf(prePaths.joinByPathCase(PathCase.ReferencePath) to value)
		}
	}.toMap()
}


/**根据指定路径[path]递归查询当前数组，返回匹配的元素列表。使用Json路径[PathCase.JsonPath]。*/
fun <T> Array<T>.deepQuery(path: String) = privateDeepQueryValue(toList(), path)

/**根据指定路径[path]递归查询当前集合，返回匹配的元素列表。使用Json路径[PathCase.JsonPath]。*/
fun <T> Iterable<T>.deepQuery(path: String) = privateDeepQueryValue(toList(), path)

/**根据指定路径[path]递归查询当前映射，返回匹配的值列表。使用Json路径[PathCase.JsonPath]。*/
fun <K, V> Map<K, V>.deepQueryValue(path: String) = privateDeepQueryValue(this, path)

private fun privateDeepQueryValue(collection: Any?, path: String): List<Any?> {
	val subPaths = path.trim().splitByPathCase(PathCase.JsonPath)
	var valueList = listOf(collection)
	for(subPath in subPaths) {
		valueList = when {
			//如果子路径表示一个列表，例如："[]"
			subPath == "[]" -> {
				valueList.flatMap { it as List<*> }
			}
			//如果子路径表示一个范围，例如："1..10"
			subPath matches "\\d+\\.\\.\\d+".toRegex() -> {
				val (fromIndex, toIndex) = subPath.split("..").map { it.toInt() }
				valueList.flatMap { (it as List<*>) }.subList(fromIndex, toIndex + 1)
			}
			//如果子路径表示一个列表索引，例如："1"
			subPath matches "\\d+".toRegex() -> {
				val index = subPath.toInt()
				valueList.map { (it as List<*>)[index] }
			}
			//如果子路径表示一个对象，例如："{}"
			subPath == "{}" -> {
				valueList.flatMap { (it as Map<*, *>).toStringKeyMap().values }
			}
			//如果子路径表示一个占位符，例如："{Category}"
			subPath matches "\\{.+}".toRegex() -> {
				valueList.flatMap { (it as Map<*, *>).toStringKeyMap().values }
			}
			//如果子路径表示一个正则表达式，例如："regex.*Name"
			subPath startsWith "regex:" -> {
				val regex = subPath.removePrefix("regex:")
				valueList.flatMap { (it as Map<*, *>).toStringKeyMap().filterKeys { k -> k matches regex.toRegex() }.values }
			}
			//如果是其他情况，例如："Name"`
			else -> {
				valueList.map { (it as Map<*, *>).toStringKeyMap()[subPath] }
			}
		}
	}
	return valueList
}


/**将当前数组转化成以键为值的映射。*/
fun <T> Array<T>.toIndexKeyMap(): Map<String, T> {
	return this.withIndex().associate { (i, e) -> Pair(i.toString(), e) }
}

/**将当前集合转化成以键为值的映射。*/
fun <T> Iterable<T>.toIndexKeyMap(): Map<String, T> {
	return this.withIndex().associate { (i, e) -> Pair(i.toString(), e) }
}

/**将当前映射转换成以字符串为键的映射。*/
fun <K, V> Map<K, V>.toStringKeyMap(): Map<String, V> {
	return this.mapKeys { it.toString() }
}


/**将当前映射转化为指定类型的对象。可指定是否递归转化[recursive]，默认为true。*/
inline fun <reified T> Map<String, Any?>.toObject(recursive: Boolean = true) = this.toObject(T::class.java, recursive)

/**将当前映射转化为指定类型的对象。可指定是否递归转化[recursive]，默认为true。*/
@NotSuitable("不存在无参构造方法时，转化需要转化元素的数组时")
fun <T> Map<String, Any?>.toObject(type: Class<T>, recursive: Boolean = true): T {
	val newObject = type.getConstructor().newInstance()
	val propertyMap = type.setterMap
	for((propertyName, setMethod) in propertyMap) {
		if(!propertyMap.containsKey(propertyName)) {
			continue
		}
		val propertyValue = this[propertyName]
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
			(propertyValue as Map<*, *>).toStringKeyMap().toObject(propertyType)
		}
		else -> null
	}
}


/**得到指定索引的值，如果出错，则返回空字符串。*/
fun Array<String>.getOrEmpty(index: Int) = this.getOrElse(index) { "" }

/**得到指定索引的值，如果出错，则返回空字符串。*/
fun List<String>.getOrEmpty(index: Int) = this.getOrElse(index) { "" }


/**去除第一行空白行。*/
fun Array<CharSequence>.dropBlank() = this.dropWhile { it.isBlank() }

/**去除最后一行空白行。*/
fun Array<CharSequence>.dropLastBlank() = this.dropLastWhile { it.isBlank() }

/**去除第一行空白行。*/
fun Iterable<CharSequence>.dropBlank() = this.dropWhile { it.isBlank() }

/**去除最后一行空白行。*/
fun List<CharSequence>.dropLastBlank() = this.dropLastWhile { it.isBlank() }


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
