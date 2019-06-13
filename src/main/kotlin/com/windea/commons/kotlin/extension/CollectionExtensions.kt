@file:Suppress("UNCHECKED_CAST")

package com.windea.commons.kotlin.extension

//路径查找方法

/**
 * 根据指定路径 [path] 查询当前映射，返回匹配的键或值列表。
 *
 * 可选的目标前缀：key，value。根路径应该表示一个对象/映射（的键）。
 *
 * @see query
 */
fun <K, V> Map<K, V>.query(path: String): List<Any?> = query(this, path)

/**
 * 根据指定路径 [path] 查询当前列表，返回匹配的元素列表。
 *
 * 可选的目标前缀：elem。根路径应该表示一个列表（的索引）。
 *
 * @see query
 */
fun <E> List<E>.query(path: String): List<Any?> = query(this, path)

/**
 * 根据指定路径 [path] 查询当前集合，返回匹配的实体列表。
 *
 * 示例： `value:#/Data/{Category}/1/Name`。
 *
 * 允许的目标前缀：
 * * `elem:` 查询列表元素。
 * * `key:` 查询映射的键。
 * * `value:` 查询映射的值。默认的目标前缀。
 *
 * 允许的子路径格式：
 * * `[]` 表示一个列表。
 * * `1..10` 表示一个所应在指定范围内的列表。包含上下限。
 * * `1` 表示一个列表的索引。
 * * `{}` 表示一个对象/映射。
 * * `{Category}` 表示一个注为指定占位符的对象/映射。
 * * `re:Name.*` 表示一个属性/键匹配指定正则的对象/映射。
 * * `Name` 表示一个对象/映射的属性/键。
 *
 * 对于`{"a":1,{"ba":21,"bb":22},"c":[31,32,33]}`
 *
 */
private fun query(collection: Any?, path: String): List<Any?> {
	val splitPaths = path.split(":", limit = 1)
	val (prefix, noPrefixPath) = when {
		splitPaths.count() > 1 -> Pair(splitPaths[0].trim(), splitPaths[1].trim())
		else -> Pair("value", path.trim())
	}
	val clearPath = noPrefixPath.removePrefix("#").removePrefix("/")
	val subPaths = clearPath.split("/")
	
	var resultList = listOf(collection)
	
	for(subPath in subPaths) {
		//TODO 重构
		resultList = when {
			//如果子路径表示一个列表，例如："[]"
			subPath == "[]" -> queryRuleMap.getValue("list").invoke(resultList, subPath)
			//如果子路径表示一个列表索引，例如："1"
			subPath matches Regex("\\d+]") -> queryRuleMap.getValue("index").invoke(resultList, subPath)
			//如果子路径表示一个范围，例如："1..10"
			subPath matches Regex("\\d+\\.\\.\\d+") -> queryRuleMap.getValue("indexRange").invoke(resultList, subPath)
			//如果子路径表示一个对象，例如："{}"
			subPath == "{}" -> queryRuleMap.getValue("map").invoke(resultList, subPath)
			//如果子路径表示一个占位符，例如："{Category}"
			subPath matches Regex("\\{.+}") -> queryRuleMap.getValue("varKey").invoke(resultList, subPath)
			//如果子路径表示一个正则表达式，例如："re:Name.*"
			subPath matches Regex("re:.*") -> queryRuleMap.getValue("regexKey").invoke(resultList, subPath)
			//如果是其他情况，例如："Name"
			else -> queryRuleMap.getValue("key").invoke(resultList, subPath)
		}
	}
	
	resultList = when(prefix) {
		"key" -> resultList.map {
			if(it is Map.Entry<*, *>) it.key else it
		}
		"value" -> resultList.map {
			if(it is Map.Entry<*, *>) it.value else it
		}
		else -> resultList
	}
	
	return resultList
}

typealias QueryRule = (resultList: List<Any?>, subPath: String) -> List<Any?>

private val queryRuleMap = mapOf<String, QueryRule>(
	"list" to { resultList, _ ->
		resultList.flatMap { it as List<Any?> }
	},
	"index" to { resultList, subPath ->
		val index = subPath.toInt()
		resultList.map { (it as List<Any?>)[index] }
	},
	"indexRange" to { resultList, subPath ->
		val (fromIndex, toIndex) = subPath.split("..").map { it.toInt() }
		resultList.flatMap { (it as List<Any?>) }.subList(fromIndex, toIndex + 1)
	},
	"map" to { resultList, _ ->
		resultList.flatMap { (it as Map<String, Any>).entries }
	},
	"key" to { resultList, subPath ->
		resultList.map { mapOf(subPath to (it as Map<String, Any?>)[subPath]).entries.first() }
	},
	"varKey" to { resultList, _ ->
		resultList.flatMap { (it as Map<String, Any>).entries }
	},
	"regexKey" to { resultList, subPath ->
		val pattern = subPath.removePrefix("re:").trim()
		resultList.flatMap { (it as Map<String, Any?>).filterKeys { k -> k matches Regex(pattern) }.entries }
	}
).withDefault { { resultList, _ -> resultList } }
