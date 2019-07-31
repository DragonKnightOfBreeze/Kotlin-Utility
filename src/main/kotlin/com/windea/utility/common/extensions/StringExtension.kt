@file:Suppress("UNUSED_PARAMETER")

package com.windea.utility.common.extensions

import com.windea.utility.common.domain.text.*
import com.windea.utility.common.enums.*
import java.io.*
import java.net.*
import java.nio.file.*
import java.text.*
import kotlin.contracts.*

/**@see kotlin.text.repeat*/
operator fun String.times(n: Int) = this.repeat(n)

/**@see kotlin.text.chunked*/
operator fun String.div(n: Int) = this.chunked(n)


/**判断字符串是否相等。忽略大小写。*/
infix fun String?.equalsIc(other: String?): Boolean {
	return this.equals(other, true)
}

/**判断字符串是否相等。忽略显示格式[StringCase]。*/
infix fun String?.equalsIsc(other: String?): Boolean {
	if(this == other) return true
	return this != null && other != null && this.switchCase(this.getCase(), other.getCase()) == other
}


/**判断当前字符串是否以指定前缀开头。*/
infix fun CharSequence.startsWith(prefix: CharSequence): Boolean {
	return this.startsWith(prefix, false)
}

/**判断当前字符串是否以任意指定前缀开头。*/
infix fun CharSequence.startsWith(prefixArray: Array<CharSequence>): Boolean {
	return prefixArray.any { this.startsWith(it, false) }
}

/**判断当前字符串是否以指定后缀结尾。*/
infix fun CharSequence.endsWith(suffix: CharSequence): Boolean {
	return this.endsWith(suffix, false)
}

/**判断当前字符串是否以任意指定后缀结尾。*/
infix fun CharSequence.endsWith(suffixArray: Array<CharSequence>): Boolean {
	return suffixArray.any { this.endsWith(it, false) }
}

/**判断当前字符串是否以指定前缀开头。忽略大小写。*/
infix fun CharSequence.startsWithIc(prefix: CharSequence): Boolean {
	return this.startsWith(prefix, true)
}

/**判断当前字符串是否以任意指定前缀开头。忽略大小写。*/
infix fun CharSequence.startsWithIc(prefixArray: Array<CharSequence>): Boolean {
	return prefixArray.any { this.startsWith(it, true) }
}

/**判断当前字符串是否以指定后缀结尾。忽略大小写。*/
infix fun CharSequence.endsWithIc(suffix: CharSequence): Boolean {
	return this.endsWith(suffix, true)
}

/**判断当前字符串是否以指定后缀结尾。忽略大小写。*/
infix fun CharSequence.endsWithIc(suffixArray: Array<CharSequence>): Boolean {
	return suffixArray.any { this.endsWith(it, true) }
}


private var enableCrossLine = false
private var prepareCrossLineSurroundingWith = false

/**执行跨行操作。*/
fun <R> List<String>.crossLine(block: (List<String>) -> R): R {
	enableCrossLine = true
	return this.let(block).also {
		enableCrossLine = false
		prepareCrossLineSurroundingWith = false
	}
}

/**执行跨行操作。*/
fun <R> Sequence<String>.crossLine(block: (Sequence<String>) -> R): R {
	enableCrossLine = true
	return this.let(block).also {
		enableCrossLine = false
		prepareCrossLineSurroundingWith = false
	}
}

/**判断当前行是否在指定的跨行前后缀之间。在[crossLine]之中调用这个方法。*/
fun String.crossLineSurroundsWith(prefix: String, suffix: String, ignoreCase: Boolean = false): Boolean {
	check(enableCrossLine) { "[ERROR] Cross line operations are not enabled. Can be enabled in crossLine { ... } block." }
	
	val isBeginBound = this.startsWith(prefix, ignoreCase)
	val isEndBound = this.startsWith(suffix, ignoreCase)
	if(isBeginBound && !prepareCrossLineSurroundingWith) prepareCrossLineSurroundingWith = true
	if(isEndBound) prepareCrossLineSurroundingWith = false
	return !isBeginBound && prepareCrossLineSurroundingWith
}

/**判断当前行是否在指定的跨行前后缀之间。在[crossLine]之中调用这个方法。*/
fun String.crossLineSurroundsWith(delimiter: String, ignoreCase: Boolean = false) =
	this.crossLineSurroundsWith(delimiter, delimiter, ignoreCase)


/**判断当前可空字符串是否为null，为空白。*/
@ExperimentalContracts
fun CharSequence?.isNullOrBlank(): Boolean {
	contract {
		returns(false) implies (this@isNullOrBlank != null)
	}
	return this == null || this.isBlank()
}


/**根据指定的正则表达式，得到当前字符串的匹配分组列表。不包含索引0的分组，列表可能为空。*/
fun String.substring(regex: Regex): List<String> {
	return regex.matchEntire(this)?.groupValues?.drop(0) ?: listOf()
}

/**根据以null隔离的从前往后和从后往前的分隔符，按顺序分割字符串。不包含分隔符时，加入以待分割字符串为参数的从默认字符串列表中取出的值。*/
fun String.substring(vararg delimiters: String?, defaultValue: (String) -> List<String>): List<String> {
	require(delimiters.count { it == null } > 1) { "[ERROR] There should be at most one null value for separator in delimiters!" }
	
	var rawString = this
	val fixedDelimiters = delimiters.filterNotNull()
	val size = fixedDelimiters.size
	val indexOfNull = delimiters.indexOf(null).let { if(it == -1) size else it }
	val result = mutableListOf<String>()
	
	for((index, delimiter) in fixedDelimiters.withIndex()) {
		if(index < indexOfNull) {
			result += rawString.substringBefore(delimiter, defaultValue(delimiter).getOrEmpty(index))
			if(index == size - 1) {
				result += rawString.substringAfter(delimiter, defaultValue(delimiter).getOrEmpty(index))
			} else {
				rawString = rawString.substringAfter(delimiter, rawString)
			}
		} else {
			result += rawString.substringBeforeLast(delimiter, defaultValue(delimiter).getOrEmpty(index))
			if(index == size - 1) {
				result += rawString.substringAfterLast(delimiter, defaultValue(delimiter).getOrEmpty(index))
			} else {
				rawString = rawString.substringAfterLast(delimiter, rawString)
			}
		}
	}
	return result
}

/**根据以null隔离的从前往后和从后往前的分隔符，按顺序分割字符串。不包含分隔符时，加入空字符串。*/
fun String.substringOrEmpty(vararg delimiters: String?): List<String> {
	return this.substring(*delimiters) { emptyList() }
}


/**
 * 基于[MessageFormat]格式化当前字符串。自动转义单引号。
 *
 * 占位符形如：`{0}`, `{1,number}`, `{2,date,shot}`。
 */
fun String.messageFormat(vararg args: Any): String {
	return MessageFormat.format(this.replace("'", "''"), *args)
}


/**根据指定的前缀[prefix]和后缀[suffix]，包围字符串，可指定是否忽略空字符串[ignoreEmpty]，默认为true。*/
fun String.surrounding(prefix: String, suffix: String, ignoreEmpty: Boolean = true): String {
	return if(ignoreEmpty && this.isEmpty()) "" else prefix + this + suffix
}

/**根据指定的前后缀[delimiter]，包围字符串，可指定是否忽略空字符串[ignoreEmpty]，默认为true。*/
fun String.surrounding(delimiter: String, ignoreEmpty: Boolean = true) = surrounding(delimiter, delimiter, ignoreEmpty)


/**去除指定字符串。*/
fun String.remove(oldValue: String, ignoreCase: Boolean = false): String {
	return this.replace(oldValue, "", ignoreCase)
}

/**去除指定正则表达式的字符串。*/
fun String.remove(regex: Regex): String {
	return this.replace(regex, "")
}

/**去除所有空格。*/
fun String.removeWhiteSpace(): String {
	return this.replace(" ", "")
}

/**去除所有空白。*/
fun String.removeBlank(): String {
	return this.replace("[\\s\\n\\r\\t]+".toRegex(), "")
}


private val escapeChars = arrayOf('\n', '\r', '\b', '\t', '\'', '\"', '\\')

private val unescapeStrings = arrayOf("\\n", "\\r", "\\n", "\\t", "\\'", "\\\"", "\\\\")

/**转义当前字符串。例如，将`\\n`转换为`\n`。*/
fun String.escape(): String {
	return buildString {
		for((escapeChar, unescapeString) in escapeChars zip unescapeStrings) {
			this.replace(unescapeString.toRegex(), escapeChar.toString())
		}
	}
}

/**反转义当前字符串。例如，将`\n`转换为`\\n`。*/
fun String.unescape(): String {
	return buildString {
		for((escapeChar, unescapeString) in escapeChars zip unescapeStrings) {
			this.replace(escapeChar.toString().toRegex(), unescapeString)
		}
	}
}


private val quoteChars = arrayOf('"', '\'', "`")

/**使用双引号/单引号/反引号包围当前字符串。默认使用双引号。*/
fun String.wrapQuote(quoteChar: Char = '"'): String {
	if(quoteChar !in quoteChars) return this
	return this.replace(quoteChar.toString(), "\\$quoteChar").surrounding(quoteChar.toString(), false)
}

/**去除当前字符串两侧的双引号/单引号/反引号。*/
fun String.unwrapQuote(): String {
	val quoteChar = this.first()
	if(quoteChar !in quoteChars) return this
	return this.removeSurrounding(quoteChar.toString()).replace("\\$quoteChar", quoteChar.toString())
}


/**将第一个字符转为大写。*/
fun String.firstCharToUpperCase(): String {
	return this[0].toUpperCase() + this.substring(1, this.length)
}

/**将第一个字符转为大写，将其他字符皆转为小写。*/
fun String.firstCharToUpperCaseOnly(): String {
	return this[0].toUpperCase() + this.substring(1, this.length).toLowerCase()
}

/**将第一个字符转为小写。*/
fun String.firstCharToLowerCase(): String {
	return this[0].toLowerCase() + this.substring(1, this.length)
}

/**将第一个字符转为小写，将其他字符皆转为小写。*/
fun String.firstCharToLowerCaseOnly(): String {
	return this[0].toLowerCase() + this.substring(1, this.length).toUpperCase()
}


/**得到当前字符串的显示格式。*/
fun String.getCase(): StringCase {
	return when {
		this matches StringCase.CamelCase.regex -> StringCase.CamelCase
		this matches StringCase.PascalCase.regex -> StringCase.PascalCase
		this matches StringCase.SnakeCase.regex -> StringCase.SnakeCase
		this matches StringCase.ScreamingSnakeCase.regex -> StringCase.ScreamingSnakeCase
		this matches StringCase.KebabCase.regex -> StringCase.KebabCase
		this matches StringCase.KebabUpperCase.regex -> StringCase.KebabUpperCase
		this matches StringCase.DotCase.regex -> StringCase.DotCase
		this matches StringCase.WhiteSpaceCase.regex -> StringCase.WhiteSpaceCase
		else -> StringCase.Unknown
	}
}

/**切换当前字符串的显示格式。*/
fun String.switchCase(fromCase: StringCase, toCase: StringCase): String {
	return this.splitByCase(fromCase).joinByCase(toCase)
}

/**根据显示格式分割当前字符串。*/
fun String.splitByCase(case: StringCase): List<String> {
	return when(case) {
		StringCase.Unknown -> listOf(this)
		StringCase.CamelCase -> this.firstCharToUpperCase().splitByWhiteSpace().split(" ")
		StringCase.PascalCase -> this.splitByWhiteSpace().split(" ")
		StringCase.SnakeCase -> this.split("_")
		StringCase.ScreamingSnakeCase -> this.split("_")
		StringCase.KebabCase -> this.split("-")
		StringCase.KebabUpperCase -> this.split("-")
		StringCase.DotCase -> this.split(".")
		StringCase.WhiteSpaceCase -> this.split(" ")
	}
}

/**根据显示格式连接当前字符串列表。*/
fun List<String>.joinByCase(case: StringCase): String {
	return when(case) {
		StringCase.Unknown -> this.first()
		StringCase.CamelCase -> this.joinToString("") { it.firstCharToUpperCaseOnly() }.firstCharToLowerCase()
		StringCase.PascalCase -> this.joinToString("") { it.firstCharToUpperCaseOnly() }
		StringCase.SnakeCase -> this.joinToString("_") { it.toLowerCase() }
		StringCase.ScreamingSnakeCase -> this.joinToString("_") { it.toUpperCase() }
		StringCase.KebabCase -> this.joinToString("-") { it.toLowerCase() }
		StringCase.KebabUpperCase -> this.joinToString("-") { it.toUpperCase() }
		StringCase.DotCase -> this.joinToString(".")
		StringCase.WhiteSpaceCase -> this.joinToString(" ")
	}
}

private fun String.splitByWhiteSpace(): String {
	return this.replace("\\B([A-Z]+)".toRegex(), " $1")
}


/**得到当前字符串的路径显示格式。*/
fun String.getPathCase(): PathCase {
	return when {
		this matches PathCase.WindowsPath.regex -> PathCase.WindowsPath
		this matches PathCase.UnixPath.regex -> PathCase.UnixPath
		this matches PathCase.ReferencePath.regex -> PathCase.ReferencePath
		this matches PathCase.JsonPath.regex -> PathCase.JsonPath
		else -> PathCase.Unknown
	}
}

/**切换当前字符串的路径显示格式。*/
fun String.switchPathCase(fromCase: PathCase, toCase: PathCase): String {
	return this.splitByPathCase(fromCase).joinByPathCase(toCase)
}

/**根据路径显示格式分割当前字符串，组成完整路径。*/
fun String.splitByPathCase(case: PathCase): List<String> {
	val fixedPath = this.removePrefix(".").removePrefix(".").removePrefix("#")
	return when(case) {
		PathCase.Unknown -> listOf(this)
		PathCase.WindowsPath -> fixedPath.removeSurrounding("\\").split("\\")
		PathCase.UnixPath -> fixedPath.removeSurrounding("/").split("/")
		PathCase.ReferencePath -> fixedPath.split("[", "].", ".")
		PathCase.JsonPath -> fixedPath.removeSurrounding("/").split("/")
	}
}

/**根据路径显示格式连接当前字符串列表，组成完整路径。*/
fun List<String>.joinByPathCase(case: PathCase, addPrefix: Boolean = true): String {
	return when(case) {
		PathCase.Unknown -> this.first()
		PathCase.WindowsPath -> this.joinToString("\\").let { if(addPrefix) "\\$it" else it }
		PathCase.UnixPath -> this.joinToString("/").let { if(addPrefix) "/$it" else it }
		PathCase.ReferencePath -> this.joinToString(".").replace("\\.(\\d*)\\.".toRegex(), "[$1].")
		PathCase.JsonPath -> "#/" + this.joinToString("/")
	}
}


/**将当前字符串转为折行文本。（去除所有换行符以及尾随空白。）*/
fun String.asWrappedText(): String {
	return this.remove("\n").trimEnd()
}

/**将当前字符串转化为多行文本。（去除首尾空白行，然后基于最后一行的缩进，去除每一行的缩进。）*/
fun String.asMultilineText(): String {
	val lines = this.lines()
	val trimmedIndent = lines.last().let { if(it.isBlank()) it.count() else 0 }
	if(trimmedIndent == 0) return this.trimIndent()
	return lines.dropBlank().dropLastBlank().joinToString("\n") { it.drop(trimmedIndent) }
}


/**去空白后，将当前字符串转化为对应的整数，发生异常则转化为默认值[defaultValue]，默认为0。*/
fun String.toIntOrDefault(defaultValue: Int = 0): Int {
	return runCatching { this.trim().toInt() }.getOrDefault(defaultValue)
}

/**去空白后，将当前字符串转化为对应的单精度浮点数，发生异常则转化为默认值[defaultValue]，默认为0.0f。*/
fun String.toFloatOrDefault(defaultValue: Float = 0.0f): Float {
	return runCatching { this.trim().toFloat() }.getOrDefault(defaultValue)
}

/**去空白后，将当前字符串转化为对应的双精度浮点数，发生异常则转化为默认值[defaultValue]，默认为0.0。*/
fun String.toDoubleOrDefault(defaultValue: Double = 0.0): Double {
	return runCatching { this.toDouble() }.getOrDefault(defaultValue)
}

/**将当前字符串转化为对应的枚举常量。*/
inline fun <reified E : Enum<E>> String.toEnumConst() = this.toEnumConst(E::class.java)

/**将当前字符串转化为对应的枚举常量。*/
fun <E : Enum<E>> String.toEnumConst(type: Class<E>): E {
	return try {
		type.enumConstants.first { it.toString() == this }
	} catch(e: Exception) {
		println("[WARN] No matched enum const found. Convert to default.")
		type.enumConstants.first()
	}
}

/**将当前字符串转化为对应的枚举常量。*/
fun String.toEnumConst(type: Class<*>): Any {
	val enumConsts = type.enumConstants ?: throw IllegalArgumentException("[ERROR] $type is not an enum class!")
	return try {
		enumConsts.first { it.toString() == this }
	} catch(e: Exception) {
		println("[WARN] No matched enum const found. Convert to default.")
		enumConsts.first()
	}
}


/**将当前字符串转化为文件。*/
fun String.toFile(): File = File(this.trim())

/**将当前字符串转化为路径。*/
fun String.toPath(): Path = Path.of(this.trim())

/**将当前字符串转化为地址。*/
fun String.toUrl(content: URL? = null, handler: URLStreamHandler? = null): URL = URL(content, this.trim(), handler)

/**将当前字符串转化为统一资源定位符。*/
fun String.toUri(): URI = URI.create(this.trim())


/**得到当前字符串对应的Markdown对象。*/
@Deprecated("")
fun String.toMarkdown(enableExtendedSyntax: Boolean = false, enableCriticalMarkup: Boolean = false): Markdown {
	return Markdown(this, enableExtendedSyntax, enableCriticalMarkup)
}

/**得到当前字符串对应的路径信息。*/
fun String.toPathInfo(): PathInfo {
	val path = this.trim().replace("/", "\\")
	val rootPath = path.substringBefore("\\")
	val (fileDirectory, fileName) = path.substring(null, "\\") { listOf("", it) }
	val (fileShotName, fileExtension) = fileName.substring(null, ".") { listOf(it, "") }
	return PathInfo(path, rootPath, fileDirectory, fileName, fileShotName, fileExtension)
}

/**得到当前字符串对应的的地址信息。*/
fun String.toUrlInfo(): UrlInfo {
	val url = this.trim()
	val (fullPath, query) = url.substring("?") { listOf(it, "") }
	val (protocol, hostAndPort, path) = fullPath.substring("://", "/") { listOf("http", "", it) }
	val (host, port) = hostAndPort.substring(":") { listOf(it, "") }
	return UrlInfo(this, fullPath, protocol, host, port, path, query)
}

/**得到当前字符串对应的查询参数映射。*/
internal fun String.toQueryParamMap(): QueryParamMap {
	val map = if(this.isEmpty()) {
		mapOf()
	} else {
		this.split("&").map { s -> s.split("=") }.groupBy({ it[0] }, { it[1] })
			.mapValues { (_, v) -> if(v.size == 1) v[0] else v }
	}
	return QueryParamMap(map)
}


