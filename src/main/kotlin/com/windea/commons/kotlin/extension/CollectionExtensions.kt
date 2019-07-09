@file:Suppress("UNCHECKED_CAST")

package com.windea.commons.kotlin.extension

import com.windea.commons.kotlin.annotation.NotTested
import java.lang.reflect.Method

typealias MList<E> = MutableList<E>

typealias MSet<E> = MutableSet<E>

typealias MMap<K, V> = MutableMap<K, V>


fun <E> mListOf(vararg elements: E) = mutableListOf(*elements)

fun <E> mSetOf(vararg elements: E) = mutableSetOf(*elements)

fun <K, V> mMapOf(vararg pairs: Pair<K, V>) = mutableMapOf(*pairs)


/**
 * 得到指定索引的元素，发生异常则得到默认值。
 */
fun <E> Array<E>.getOrDefault(index: Int, defaultValue: E): E {
	return runCatching { this[index] }.getOrDefault(defaultValue)
}

/**
 * 得到指定索引的元素，发生异常则得到默认值。
 */
fun <E> List<E>.getOrDefault(index: Int, defaultValue: E): E {
	return runCatching { this[index] }.getOrDefault(defaultValue)
}


/**
 * 根据指定路径 [path] 查询当前列表，返回匹配的元素列表。
 *
 * @see collectionQueryValue
 */
fun <E> List<E>.query(path: String) = collectionQueryValue(this, path)

/**
 * 根据指定路径 [path] 查询当前集，返回匹配的元素列表。
 *
 * @see collectionQueryValue
 */
fun <E> Set<E>.query(path: String) = collectionQueryValue(this.toList(), path)

/**
 * 根据指定路径 [path] 查询当前映射，返回匹配的值列表。
 *
 * @see collectionQueryValue
 */
fun <K, V> Map<K, V>.queryValue(path: String) = collectionQueryValue(this, path)

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
private fun collectionQueryValue(collection: Any?, path: String): List<Any?> {
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


/**
 * 将映射转化为对象。默认不递归转化。
 *
 * 该对象是标准java bean的形式，必须带有无参构造方法，必须带有数个set方法，不能带有实体类属性。
 *
 * WARN 转化非基本类型、非字符串数组时，该方法会出错！
 */
fun <T> Map<String, Any?>.mapToObject(type: Class<T>, recursive: Boolean = false) = collectionMapToObject(this, type, recursive)

@NotTested("不存在无参构造方法，属性不具有set方法，元素需要转化的数组，其他特殊情况？")
private fun <T> collectionMapToObject(map: Map<String, Any?>, type: Class<T>, recursive: Boolean = false): T {
	val newObject = type.getConstructor().newInstance()
	val propertyMap = getPropertyMap(type)
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

private fun getPropertyMap(type: Class<*>): Map<String, Method> {
	return type.methods.filter { it.name.startsWith("set") }.associateBy { it.name.substring(3).firstCharToLowerCase() }
}

private fun convertProperty(propertyType: Class<*>, propertyValue: Any?, recursive: Boolean = false): Any? {
	return when {
		propertyType.isPrimitive || propertyType.isCharSequence() -> propertyValue
		propertyType.isEnum -> propertyValue.toString().toEnumConstUnchecked(propertyType)
		//使用高阶函数后，无法直接得到运行时泛型
		propertyType.isArray -> (propertyValue as Array<*>)
		propertyType.isList() -> (propertyValue as Iterable<*>).toList().map {
			if(it == null) null else convertProperty(it.javaClass, it, recursive)
		}
		propertyType.isSet() -> (propertyValue as Iterable<*>).toSet().map {
			if(it == null) null else convertProperty(it.javaClass, it, recursive)
		}.toSet()
		propertyType.isMap() -> (propertyValue as Map<*, *>).mapValues { (_, v) ->
			if(v == null) null else convertProperty(v.javaClass, v, recursive)
		}
		propertyType.isSerializable() && recursive -> {
			collectionMapToObject((propertyValue as Map<String, Any?>), propertyType)
		}
		else -> null
	}
}


/**
 * 向下递归平滑映射当前列表。
 */
fun <E> List<E>.deepFlatMap() = collectionDeepFlatMap(this.toIndexedMap(), mutableListOf())

/**
 * 向下递归平滑映射当前集。
 */
fun <E> Set<E>.deepFlatMap() = collectionDeepFlatMap(this.toIndexedMap(), mutableListOf())

/**
 * 向下递归平滑映射当前映射。
 */
fun <K, V> Map<K, V>.deepFlatMap() = collectionDeepFlatMap(this as Map<String, Any?>, mutableListOf())

@NotTested("某些特殊情况？")
private fun collectionDeepFlatMap(map: Map<String, Any?>, prePaths: MutableList<String>): Map<String, Any?> {
	return map.flatMap { (key, value) ->
		prePaths += key
		when(value) {
			is Map<*, *> -> collectionDeepFlatMap(value as Map<String, Any?>, prePaths).toList()
			is List<*> -> collectionDeepFlatMap(value.toIndexedMap(), prePaths).toList()
			else -> {
				val fullPath = prePaths.joinToString(".").replace(Regex("\\.(\\d*)\\."), "[$1].")
				listOf(Pair(fullPath, value))
			}
		}
	}.toMap()
}


/**
 * 将当前列表转化成以键为值的映射。
 */
fun <E> List<E>.toIndexedMap(): Map<String, E> = collectionToIndexedMap(this)

/**
 * 将当前集转化成以键为值的映射。
 */
fun <E> Set<E>.toIndexedMap(): Map<String, E> = collectionToIndexedMap(this.toList())

private fun <E> collectionToIndexedMap(list: List<E>): Map<String, E> {
	return list.withIndex().map { (i, e) -> Pair(i.toString(), e) }.toMap()
}
