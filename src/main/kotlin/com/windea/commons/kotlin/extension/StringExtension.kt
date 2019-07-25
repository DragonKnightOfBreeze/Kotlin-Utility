@file:Suppress("UNUSED_PARAMETER")

package com.windea.commons.kotlin.extension

import com.windea.commons.kotlin.annotation.*
import java.text.*

/**判断可空字符串是否为null，为空白。*/
fun CharSequence?.isNullOrBlank(): Boolean {
	return this == null || this.isBlank()
}


/**判断字符串是否以指定前缀开头。*/
infix fun CharSequence.startsWith(prefix: CharSequence): Boolean {
	return this.startsWith(prefix)
}

/**判断字符串是否以任意指定前缀开头。*/
infix fun CharSequence.startsWith(prefixArray: Array<CharSequence>): Boolean {
	return prefixArray.any { this.startsWith(it) }
}

/**判断字符串是否以指定后缀结尾。*/
infix fun CharSequence.endsWith(suffix: CharSequence): Boolean {
	return this.endsWith(suffix)
}

/**判断字符串是否以任意指定后缀结尾。*/
infix fun CharSequence.endsWith(suffixArray: Array<CharSequence>): Boolean {
	return suffixArray.any { this.endsWith(it) }
}

/**判断字符串是否以指定前缀开头。忽略大小写。*/
infix fun CharSequence.startsWithIc(prefix: CharSequence): Boolean {
	return this.startsWith(prefix, ignoreCase = true)
}

/**判断字符串是否以任意指定前缀开头。忽略大小写。*/
infix fun CharSequence.startsWithIc(prefixArray: Array<CharSequence>): Boolean {
	return prefixArray.any { this.startsWith(it, ignoreCase = true) }
}

/**判断字符串是否以指定后缀结尾。忽略大小写。*/
infix fun CharSequence.endsWithIc(suffix: CharSequence): Boolean {
	return this.endsWith(suffix, ignoreCase = true)
}

/**判断字符串是否以指定后缀结尾。忽略大小写。*/
infix fun CharSequence.endsWithIc(suffixArray: Array<CharSequence>): Boolean {
	return suffixArray.any { this.endsWith(it, ignoreCase = true) }
}


operator fun String.times(n: Int) = this.repeat(n)

operator fun String.div(n: Int) = this.chunked(n)


/**根据从前往后以及从后往前的分隔符数组，按顺序分割字符串。不包含分隔符时，加入以索引和待分割字符串为参数的计算得到的值。*/
fun String.substringOrElse(delimiters: Array<String>, lastDelimiters: Array<String>, defaultValue: (Int, String) -> String): List<String> {
	var rawString = this
	val delimiterSize = delimiters.size
	val allDelimiterSize = delimiterSize + lastDelimiters.size
	val result = mutableListOf<String>()
	
	for((index, delimiter) in delimiters.withIndex()) {
		result += rawString.substringBefore(delimiter, defaultValue(index, rawString))
		if(index < allDelimiterSize - 1) {
			rawString = rawString.substringAfter(delimiter, rawString)
		} else {
			result += rawString.substringAfter(delimiter, defaultValue(index, rawString))
		}
	}
	for((index, delimiter) in lastDelimiters.withIndex().mapIndices { it + delimiterSize }) {
		result += rawString.substringBeforeLast(delimiter, defaultValue(index, rawString))
		if(index < allDelimiterSize - 1) {
			rawString = rawString.substringAfterLast(delimiter, rawString)
		} else {
			result += rawString.substringAfterLast(delimiter, defaultValue(index, rawString))
		}
	}
	return result
}

/**根据从前往后以及从后往前的分隔符数组，按顺序分割字符串。不包含分隔符时，加入默认值。*/
fun String.substringOrDefault(delimiters: Array<String>, lastDelimiters: Array<String>, defaultValue: String) =
	this.substringOrElse(delimiters, lastDelimiters) { _, _ -> defaultValue }

/**根据从前往后以及从后往前的分隔符数组，按顺序分割字符串。不包含分隔符时，加入空字符串。*/
fun String.substringOrEmpty(delimiters: Array<String>, lastDelimiters: Array<String>) =
	this.substringOrElse(delimiters, lastDelimiters) { _, _ -> "" }

/**根据从前往后以及从后往前的分隔符数组，按顺序分割字符串。不包含分隔符时，加入从默认字符串列表中对应索引取出的值。*/
fun String.substringWithDefault(delimiters: Array<String>, lastDelimiters: Array<String>, defaultValue: (String) -> List<String>) =
	this.substringOrElse(delimiters, lastDelimiters) { index, delimiter -> defaultValue(delimiter)[index] }


/**根据从前往后以及从后往前的分隔符数组，按顺序分割字符串。不包含分隔符时，加入以索引和待分割字符串为参数的计算得到的值。*/
fun String.substringOrElse(delimiters: Array<Char>, lastDelimiters: Array<Char>, defaultValue: (Int, String) -> String): List<String> {
	var rawString = this
	val delimiterSize = delimiters.size
	val allDelimiterSize = delimiterSize + lastDelimiters.size
	val result = mutableListOf<String>()
	
	for((index, delimiter) in delimiters.withIndex()) {
		result += rawString.substringBefore(delimiter, defaultValue(index, rawString))
		if(index < allDelimiterSize - 1) {
			rawString = rawString.substringAfter(delimiter, rawString)
		} else {
			result += rawString.substringAfter(delimiter, defaultValue(index, rawString))
		}
	}
	for((index, delimiter) in lastDelimiters.withIndex().mapIndices { it + delimiterSize }) {
		result += rawString.substringBeforeLast(delimiter, defaultValue(index, rawString))
		if(index < allDelimiterSize - 1) {
			rawString = rawString.substringAfterLast(delimiter, rawString)
		} else {
			result += rawString.substringAfterLast(delimiter, defaultValue(index, rawString))
		}
	}
	return result
}

/**根据从前往后以及从后往前的分隔符数组，按顺序分割字符串。不包含分隔符时，加入默认值。*/
fun String.substringOrDefault(delimiters: Array<Char>, lastDelimiters: Array<Char>, defaultValue: String) =
	this.substringOrElse(delimiters, lastDelimiters) { _, _ -> defaultValue }

/**根据从前往后以及从后往前的分隔符数组，按顺序分割字符串。不包含分隔符时，加入空字符串。*/
fun String.substringOrEmpty(delimiters: Array<Char>, lastDelimiters: Array<Char>) =
	this.substringOrElse(delimiters, lastDelimiters) { _, _ -> "" }

/**根据从前往后以及从后往前的分隔符数组，按顺序分割字符串。不包含分隔符时，加入从默认字符串列表中对应索引取出的值。*/
fun String.substringWithDefault(delimiters: Array<Char>, lastDelimiters: Array<Char>, defaultValue: (String) -> List<String>) =
	this.substringOrElse(delimiters, lastDelimiters) { index, delimiter -> defaultValue(delimiter)[index] }


/**
 * 基于[MessageFormat]格式化当前字符串。自动转义单引号。
 *
 * 占位符形如：`{0}`, `{1,number}`, `{2,date,shot}`。
 */
fun String.messageFormat(vararg args: Any): String {
	return MessageFormat.format(this.replace("'", "''"), *args)
}


/**根据指定的前缀[prefix]和后缀[suffix]，包围字符串，可指定是否忽略空字符串[ignoreEmpty]，默认为true。*/
fun String.surround(prefix: String, suffix: String, ignoreEmpty: Boolean = true): String {
	val isEmpty = ignoreEmpty && this.isEmpty()
	val result = prefix + this + suffix
	return if(isEmpty) "" else result
}

/**根据指定的前后缀[delimiter]，包围字符串，可指定是否忽略空字符串[ignoreEmpty]，默认为true。*/
fun String.surround(delimiter: String, ignoreEmpty: Boolean = true) = surround(delimiter, delimiter, ignoreEmpty)


/**去除字符串中的所有空格。*/
fun String.removeWhiteSpace(): String {
	return this.replace(" ", "")
}

/**去除字符串中的所有空白。*/
fun String.removeBlank(): String {
	return this.replace("[\\s\\n\\r\\t]+".toRegex(), "")
}


/**转义字符串。例如，将`\\n`转换为`\n`。*/
fun String.escape(): String {
	return buildString {
		for((escapeChar, unescapeString) in escapeChars zip unescapeStrings) {
			this.replace(Regex(unescapeString), escapeChar)
		}
	}
}

/**反转义字符串。例如，将`\n`转换为`\\n`。*/
fun String.unescape(): String {
	return buildString {
		for((escapeChar, unescapeString) in escapeChars zip unescapeStrings) {
			this.replace(Regex(escapeChar), unescapeString)
		}
	}
}

private val escapeChars = arrayOf("\n", "\r", "\b", "\t", "\'", "\"", "\\")

private val unescapeStrings = arrayOf("\\n", "\\r", "\\n", "\\t", "\\'", "\\\"", "\\\\")

/**使用双引号/单引号/反引号包围字符串。默认使用双引号。*/
fun String.quote(delimiter: String = "\""): String {
	if(delimiter !in quoteChars) return this
	return this.surround(delimiter, ignoreEmpty = false)
}

/**去除字符串两侧的双引号/单引号/反引号。*/
fun String.unquote(): String {
	return quoteChars.fold(this) { init, quoteChar -> init.replace(quoteChar, "") }
}

private val quoteChars = arrayOf("\"", "'", "`")


/**将第一个字符转为大写。*/
fun String.firstCharToUpperCase(): String {
	return this[0].toUpperCase() + this.substring(1, this.length)
}

/**仅将第一个字符转为大写。*/
fun String.firstCharToUpperCaseOnly(): String {
	return this[0].toUpperCase() + this.substring(1, this.length).toLowerCase()
}

/**将第一个字符转为小写。*/
fun String.firstCharToLowerCase(): String {
	return this[0].toLowerCase() + this.substring(1, this.length)
}

/**仅将第一个字符转为小写。*/
fun String.firstCharToLowerCaseOnly(): String {
	return this[0].toLowerCase() + this.substring(1, this.length).toUpperCase()
}


/**检查当前字符串的显示格式。*/
@NotTested
fun String.checkCase(): StringCase {
	return when {
		this.matches("^[a-z]+([A-Z][a-z]*)*$".toRegex()) -> StringCase.CamelCase
		this.matches("^([A-Z][a-z]*)*$".toRegex()) -> StringCase.PascalCase
		this.matches("^[A-Z]+(_[A-Z]+)*$".toRegex()) -> StringCase.ScreamingSnakeCase
		this.matches("^[a-z]+(_[a-z]+)*$".toRegex()) -> StringCase.SnakeCase
		this.matches("^[a-z]+(-[a-z]+)*$".toRegex()) -> StringCase.KebabCase
		this.matches("^[a-zA-z_]+(\\.[a-zA-z_]+)*$".toRegex()) -> StringCase.DotCase
		this.matches("^[a-zA-Z]+(\\s[a-zA-Z]+)*$".toRegex()) -> StringCase.WhiteSpaceCase
		this.matches("^[^/]+(/[^/]+)*$".toRegex()) -> StringCase.LeftSepCase
		this.matches("^[^\\\\]+(\\\\[^\\\\]+)*$".toRegex()) -> StringCase.RightSepCase
		else -> StringCase.Other
	}
}

/**转换显示格式。*/
fun String.switchCase(fromCase: StringCase, toCase: StringCase): String {
	return this.splitByCase(fromCase).concatByCase(toCase)
}

/**根据显示格式分割当前字符串。*/
fun String.splitByCase(case: StringCase): List<String> {
	return when(case) {
		StringCase.Other -> listOf(this)
		StringCase.CamelCase -> this.firstCharToUpperCase().splitWordByWhiteSpace().split(" ")
		StringCase.PascalCase -> this.splitWordByWhiteSpace().split(" ")
		StringCase.ScreamingSnakeCase -> this.split("_")
		StringCase.SnakeCase -> this.split("_")
		StringCase.KebabCase -> this.split("-")
		StringCase.DotCase -> this.split(".")
		StringCase.WhiteSpaceCase -> this.split(" ")
		StringCase.LeftSepCase -> this.split("\\")
		StringCase.RightSepCase -> this.split("/")
	}
}

/**根据显示格式连接当前字符串列表。*/
fun List<String>.concatByCase(case: StringCase): String {
	return when(case) {
		StringCase.Other -> this[0]
		StringCase.CamelCase -> this.joinToString("") { it.firstCharToUpperCaseOnly() }.firstCharToLowerCase()
		StringCase.PascalCase -> this.joinToString("") { it.firstCharToUpperCaseOnly() }
		StringCase.ScreamingSnakeCase -> this.joinToString("_") { it.toUpperCase() }
		StringCase.SnakeCase -> this.joinToString("_") { it.toLowerCase() }
		StringCase.KebabCase -> this.joinToString("_") { it.toLowerCase() }
		StringCase.DotCase -> this.joinToString(".")
		StringCase.WhiteSpaceCase -> this.joinToString(" ")
		StringCase.LeftSepCase -> this.joinToString("\\")
		StringCase.RightSepCase -> this.joinToString("/")
	}
}

private fun String.splitWordByWhiteSpace(): String {
	return this.replace("\\B([A-Z]+)".toRegex(), " $1")
}

/**字符串的显示格式。*/
enum class StringCase {
	/**OtH_e r Ca  SE。*/
	Other,
	/**camelCase。*/
	CamelCase,
	/**PascalCase。*/
	PascalCase,
	/**SCREAMING_SNAKE_CASE。*/
	ScreamingSnakeCase,
	/**snake_case。*/
	SnakeCase,
	/**kebab-case。*/
	KebabCase,
	/**dot.case。*/
	DotCase,
	/**whiteSpace case。*/
	WhiteSpaceCase,
	/**lSep\\case。*/
	LeftSepCase,
	/**rSep/case。*/
	RightSepCase
}


/**得到对应的路径信息。*/
fun String.toPathInfo(): PathInfo {
	val (fileDir, fileName) = this.substringWithDefault(arrayOf(), arrayOf("\\")) {
		listOf("", it)
	}
	val (fileShotName, fileExtension) = fileName.substringWithDefault(arrayOf(), arrayOf(".")) {
		listOf(it, "")
	}
	return PathInfo(this, fileDir, fileName, fileShotName, fileExtension)
}

/**路径信息。*/
data class PathInfo(
	/**文件路径。*/
	val filePath: String,
	/**文件所在文件夹。*/
	val fileDirectory: String,
	/**文件名。*/
	val fileName: String,
	/**不包含扩展名在内的文件名。*/
	val fileShotName: String,
	/**包含"."的文件扩展名。*/
	val fileExtension: String
) {
	/**是否存在上一级文件夹。*/
	val hasFileDirectory = fileDirectory.isNotEmpty()
	/**是否存在文件扩展名。*/
	val hasFileExtension = fileExtension.isNotEmpty()
	
	
	/**更改文件所在文件夹为新的文件夹[newFileDirectory]。*/
	fun changeFileDirectory(newFileDirectory: String): String {
		return newFileDirectory + "\\" + fileName
	}
	
	/**更改文件名为新的文件名[newFileName]。*/
	fun changeFileName(newFileName: String): String {
		return fileDirectory + "\\" + newFileName
	}
	
	/**更改不包含扩展名在内的文件名为新的文件名[newFileShotName]，可指定是否返回全路径[returnFullPath]，默认为true。*/
	fun changeFileShotName(newFileShotName: String, returnFullPath: Boolean = true): String {
		val newFileName = newFileShotName + fileExtension
		return if(returnFullPath) fileDirectory + "\\" + newFileName else newFileName
	}
	
	/**更改文件扩展名为新的扩展名[newFileExtension]，可指定是否返回全路径[returnFullPath]，默认为true。*/
	fun changeFileExtension(newFileExtension: String, returnFullPath: Boolean = true): String {
		val newFileName = fileShotName + newFileExtension
		return if(returnFullPath) fileDirectory + "\\" + newFileName else newFileName
	}
}


/**得到对应的的地址信息。*/
fun String.toUrlInfo(): UrlInfo {
	val (fullPath, params) = this.substringWithDefault(arrayOf("?"), arrayOf()) {
		listOf(it, "")
	}
	val (protocol, hostAndPort, path) = fullPath.substringWithDefault(arrayOf("://", "/"), arrayOf()) {
		listOf("http", "", it)
	}
	val (host, port) = hostAndPort.substringWithDefault(arrayOf(":"), arrayOf()) {
		listOf(it, "")
	}
	val queryParamMap = when {
		params.isEmpty() -> mapOf()
		else -> params.split("&").map { s -> s.split("=") }
			.groupBy({ it[0] }, { it[1] })
			.mapValues { (_, v) -> if(v.size == 1) v[0] else v }
	}
	return UrlInfo(this, fullPath, protocol, host, port, path, queryParamMap)
}

/**地址信息。*/
data class UrlInfo(
	/**完整地址。*/
	val url: String,
	/**排除查询参数的完整路径。*/
	val fullPath: String,
	/**协议。默认为http。*/
	val protocol: String,
	/**主机。*/
	val host: String,
	/**端口。*/
	val port: String,
	/**路径*/
	val path: String,
	/**查询参数映射。*/
	val queryParamMap: Map<String, Any>
) {
	/**是否存在查询参数。*/
	val hasQueryParam = queryParamMap.isNotEmpty()
}


/**去空格后，转化为对应的整数，发生异常则转化为默认值[defaultValue]，默认为0。*/
fun String.toIntOrDefault(defaultValue: Int = 0): Int {
	return runCatching { this.trim().toInt() }.getOrDefault(defaultValue)
}

/**去空格后，转化为对应的单精度浮点数，发生异常则转化为默认值[defaultValue]，默认为0.0f。*/
fun String.toFloatOrDefault(defaultValue: Float = 0.0f): Float {
	return runCatching { this.trim().toFloat() }.getOrDefault(defaultValue)
}

/**去空格后，转化为对应的双精度浮点数，发生异常则转化为默认值[defaultValue]，默认为0.0。*/
fun String.toDoubleOrDefault(defaultValue: Double = 0.0): Double {
	return runCatching { this.toDouble() }.getOrDefault(defaultValue)
}

/**将字符串转化为对应的枚举常量。*/
fun <E : Enum<E>> String.toEnumConst(type: Class<E>): E {
	val enumConsts = type.enumConstants
	val constName = this.trim()
	return try {
		enumConsts.first { it.toString() == constName }
	} catch(e: Exception) {
		println("[WARN] No matched enum const found. Convert to default. Enum: ${type.name}, Const: $constName.")
		enumConsts[0]
	}
}

/**将字符串转化为对应的枚举常量。*/
fun String.toEnumConst(type: Class<*>): Any {
	val enumConsts = type.enumConstants ?: throw IllegalArgumentException("[ERROR] $type is not a enum class!")
	val constName = this.trim()
	return try {
		enumConsts.first { it.toString() == constName }
	} catch(e: Exception) {
		println("[WARN] No matched enum const found. Convert to default. Enum: ${type.name}, Const: $constName.")
		enumConsts[0]
	}
}


/**过滤空字符串。*/
fun Array<CharSequence>.filterNotEmpty() = this.filter { it.isNotEmpty() }

/**过滤空白字符串。*/
fun Array<CharSequence>.filterNotBlank() = this.filter { it.isNotEmpty() }

/**过滤空字符串。*/
fun Iterable<CharSequence>.filterNotEmpty() = this.filter { it.isNotEmpty() }

/**过滤空白字符串。*/
fun Iterable<CharSequence>.filterNotBlank() = this.filter { it.isNotEmpty() }
