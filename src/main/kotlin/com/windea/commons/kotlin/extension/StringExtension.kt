@file:Suppress("UNUSED_PARAMETER")

package com.windea.commons.kotlin.extension

/**判断字符串是否以指定前缀开头。*/
infix fun String.startsWith(prefix: String): Boolean {
	return this.startsWith(prefix, false)
}

/**判断字符串是否以任意指定前缀开头。*/
infix fun String.startsWith(prefixArray: Array<String>): Boolean {
	for(prefix in prefixArray) {
		if(this.startsWith(prefix, false)) return true
	}
	return false
}

/**判断字符串是否以指定后缀结尾。*/
infix fun String.endsWith(suffix: String): Boolean {
	return this.endsWith(suffix, false)
}

/**判断字符串是否以任意指定后缀结尾。*/
infix fun String.endsWith(prefixArray: Array<String>): Boolean {
	for(prefix in prefixArray) {
		if(this.endsWith(prefix, false)) return true
	}
	return false
}

/**判断字符串是否以指定前缀开头。忽略大小写。*/
infix fun String.startsWithIc(prefix: String): Boolean {
	return this.startsWith(prefix, true)
}

/**判断字符串是否以任意指定前缀开头。忽略大小写。*/
infix fun String.startsWithIc(prefixArray: Array<String>): Boolean {
	for(prefix in prefixArray) {
		if(this.startsWith(prefix, true)) return true
	}
	return false
}

/**判断字符串是否以指定后缀结尾。忽略大小写。*/
infix fun String.endsWithIc(suffix: String): Boolean {
	return this.endsWith(suffix, true)
}

/**判断字符串是否以指定后缀结尾。忽略大小写。*/
infix fun String.endsWithIc(prefixArray: Array<String>): Boolean {
	for(prefix in prefixArray) {
		if(this.endsWith(prefix, true)) return true
	}
	return false
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
	return this.surround(delimiter, false)
}

/**去除字符串两侧的双引号/单引号/反引号。*/
fun String.unquote(): String {
	return this.chaining(quoteChars) { this.replace(it, "") }
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


/**转换显示格式。*/
fun String.switchCase(fromCase: StringCase, toCase: StringCase): String {
	return concatByCase(splitByCase(this, fromCase), toCase)
}

private fun splitByCase(string: String, case: StringCase): List<String> {
	return when(case) {
		StringCase.Other -> listOf(string)
		StringCase.CamelCase -> string.firstCharToUpperCase().splitWordByWhiteSpace().split(" ")
		StringCase.PascalCase -> string.splitWordByWhiteSpace().split(" ")
		StringCase.ScreamingSnakeCase -> string.split("_")
		StringCase.SnakeCase -> string.split("_")
		StringCase.KebabCase -> string.split("-")
		StringCase.DotCase -> string.split(".")
		StringCase.WhiteSpaceCase -> string.split(" ")
		StringCase.LSepCase -> string.split("\\")
		StringCase.RSepCase -> string.split("/")
	}
}

private fun concatByCase(splitStrings: List<String>, case: StringCase): String {
	return when(case) {
		StringCase.Other -> splitStrings[0]
		StringCase.CamelCase -> splitStrings.joinToString("") { it.firstCharToUpperCaseOnly() }.firstCharToLowerCase()
		StringCase.PascalCase -> splitStrings.joinToString("") { it.firstCharToUpperCaseOnly() }
		StringCase.ScreamingSnakeCase -> splitStrings.joinToString("_") { it.toUpperCase() }
		StringCase.SnakeCase -> splitStrings.joinToString("_") { it.toLowerCase() }
		StringCase.KebabCase -> splitStrings.joinToString("_") { it.toLowerCase() }
		StringCase.DotCase -> splitStrings.joinToString(".")
		StringCase.WhiteSpaceCase -> splitStrings.joinToString(" ")
		StringCase.LSepCase -> splitStrings.joinToString("\\")
		StringCase.RSepCase -> splitStrings.joinToString("/")
	}
}

private fun String.splitWordByWhiteSpace(): String {
	return this.replace(Regex("\\B([A-Z]+)"), " $1")
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
	LSepCase,
	/**rSep/case。*/
	RSepCase
}


/**得到对应的路径信息。*/
fun String.toPathInfo(): PathInfo {
	val fileNameIndex = this.lastIndexOf("\\")
	val fileDir = if(fileNameIndex == -1) "" else this.substring(0, fileNameIndex)
	val fileName = if(fileNameIndex == -1) this else this.substring(fileNameIndex + 1)
	
	val fileExtIndex = fileName.lastIndexOf(".")
	val fileShotName = if(fileExtIndex == -1) fileName else fileName.substring(0, fileExtIndex)
	val fileExt = if(fileExtIndex == -1) "" else fileName.substring(fileExtIndex)
	
	return PathInfo(fileDir, fileName, fileShotName, fileExt)
}

/**路径信息。*/
class PathInfo(
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
	val queryParamIndex = this.lastIndexOf("?")
	val fullPath = if(queryParamIndex == -1) this else this.substring(0, queryParamIndex)
	val paramSnippet = if(queryParamIndex == -1) "" else this.substring(queryParamIndex + 1)
	
	val queryParamMap = when {
		paramSnippet.isEmpty() -> mapOf()
		else -> paramSnippet.split("&").map { s -> s.split("=") }
			.groupBy({ it[0] }, { it[1] })
			.mapValues { (_, v) -> if(v.size == 1) v[0] else v }
	}
	
	return UrlInfo(fullPath, queryParamMap)
}

/**
 * 地址信息。
 *
 * TODO 包含协议、端口等更多信息。
 */
class UrlInfo(
	/**包含协议、端口等的完整路径。*/
	val fullPath: String,
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
fun Array<String>.filterNotEmpty() = this.filter { it.isNotEmpty() }

/**过滤空白字符串。*/
fun Array<String>.filterNotBlank() = this.filter { it.isNotEmpty() }

/**过滤空字符串。*/
fun Iterable<String>.filterNotEmpty() = this.filter { it.isNotEmpty() }

/**过滤空白字符串。*/
fun Iterable<String>.filterNotBlank() = this.filter { it.isNotEmpty() }
