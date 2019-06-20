@file:Suppress("UNCHECKED_CAST")

package com.windea.commons.kotlin.extension

/**
 * 将当前列表转化成以键为值的映射。
 */
fun <E> List<E>.toIndexedMap(): Map<String, E> {
	return this.withIndex().map { (i, e) -> Pair(i.toString(), e) }.toMap()
}


/**
 * 得到当前映射中指定路径 [path] 的值。
 */
fun <K, V> Map<K, V>.getValueByPath(path: String): Any? {
	val subPaths = path.split(".")
	var subValue = this as Map<String, Any?>
	for((index, subPath) in subPaths.withIndex()) {
		if(index == subPaths.lastIndex) {
			return subValue[subPath]
		} else if(subValue[subPath] is Map<*, *>) {
			subValue = subValue[subPath] as Map<String, Any?>
		} else {
			return null
		}
	}
	return null
}

/**
 * 设置当前映射中指定路径 [path] 的值，如果不存在，则持续向下创建。
 */
fun <K, V> MutableMap<K, V>.setValueByPath(path: String, value: Any?) {
	val subPaths = path.split(".")
	var subValue = this as MutableMap<String, Any?>
	for((index, subPath) in subPaths.withIndex()) {
		if(index == subPaths.lastIndex) {
			subValue[subPath] = value
		} else if(subValue[subPath] is MutableMap<*, *>) {
			subValue = subValue[subPath] as MutableMap<String, Any?>
		} else if(subPath !in subValue) {
			subValue[subPath] = mutableMapOf<String, Any?>()
			subValue = subValue[subPath] as MutableMap<String, Any?>
		} else {
			return
		}
	}
}


/**
 * 根据指定路径 [path] 查询当前映射，返回匹配的值列表。
 *
 * 示例： `#/Data/{Category}/1/Name`。
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
fun <K, V> Map<K, V>.queryValue(path: String): List<Any?> {
	return queryValue(this, path)
}

private fun queryValue(collection: Any?, path: String): List<Any?> {
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
 * 向下递归平滑映射当前列表。
 */
fun <E> List<E>.deepFlatMap(): Map<String, Any?> {
	return deepFlatMap(toIndexedMap(), mutableListOf())
}

/**
 * 向下递归平滑映射当前映射。
 */
fun <K, V> Map<K, V>.deepFlatMap(): Map<String, Any?> {
	return deepFlatMap(this as Map<String, Any?>, mutableListOf())
}

private fun deepFlatMap(map: Map<String, Any?>, prePaths: MutableList<String>): Map<String, Any?> {
	return map.flatMap { (key, value) ->
		prePaths += key
		if(value is Map<*, *>) {
			deepFlatMap(value as Map<String, Any?>, prePaths).toList()
		} else if(value is List<*>) {
			deepFlatMap(value.toIndexedMap(), prePaths).toList()
		} else {
			val fullPath = prePaths.joinToString(".").replace(Regex("\\.(\\d*)\\."), "[$1].")
			listOf(Pair(fullPath, value))
		}
	}.toMap()
}
