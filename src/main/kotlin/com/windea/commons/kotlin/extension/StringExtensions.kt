package com.windea.commons.kotlin.extension

import java.util.function.Predicate

/**
 * 如果满足条件 [condition]，则保留这段文本。
 */
fun String.where(condition: Boolean): String {
	return if(condition) "" else this
}

/**
 * 如果满足条件预测 [predicate]，则保留这段文本。
 */
fun <T : Any> String.where(pointer: T, predicate: Predicate<T>): String {
	val condition = predicate.test(pointer)
	return where(condition)
}


/**
 * 根据指定的前缀 [prefix] 和后缀 [suffix]，包围字符串，可指定是否忽略空字符串 [ignoreEmpty]，默认为true。
 */
fun String.surround(prefix: String, suffix: String, ignoreEmpty: Boolean = true): String {
	val condition = ignoreEmpty && this.isEmpty()
	val result = prefix + this + suffix
	return if(condition) "" else result
}

/**
 * 根据指定的前后缀 [fix]，包围字符串，可指定是否忽略空字符串 [ignoreEmpty]，默认为true。
 */
fun String.surround(fix: String, ignoreEmpty: Boolean = true): String {
	return surround(fix, fix, ignoreEmpty)
}


/**
 * 转义字符串。例如，将`\\n`转换为`\n`。
 */
fun String.escape(): String {
	return this.replace("\\n", "\n").replace("\\r", "\r").replace("\\t", "\t")
}

/**
 * 反转义字符串。例如，将`\n`转换为`\\n`。
 */
fun String.unescape(): String {
	return this.replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t")
}

/**
 * 得到字符串的显示格式。
 */
fun String.getCase() {
	TODO()
}

/**
 * 转换字符串的显示格式。
 */
fun String.switchCase(fromCase: StringCase, toCase: StringCase): String {
	TODO()
}

/**
 * 字符串的显示格式。
 */
enum class StringCase {
	/**
	 * OtH_e r。
	 */
	Other,
	/**
	 * camelCase。
	 */
	CamelCase,
	/**
	 * PascalCase。
	 */
	PascalCase,
	/**
	 * SCREAMING_SNAKE_CASE。
	 */
	ScreamingSnakeCase,
	/**
	 * snake_case。
	 */
	SnakeCase,
	/**
	 * kebab-case。
	 */
	KebabCase,
	/**
	 * dot.case。
	 */
	DotCase,
	/**
	 * whiteSpace case。
	 */
	WhiteSpaceCase,
	/**
	 * lSep\\case。
	 */
	LSepCase,
	/**
	 * rSep/case。
	 */
	RSepCase
}


/**
 * 得到对应的路径信息。
 */
fun String.toPathInfo(): PathInfo {
	val fileNameIndex = this.lastIndexOf("\\")
	val fileDir = if(fileNameIndex == -1) "" else this.substring(0, fileNameIndex)
	val fileName = if(fileNameIndex == -1) this else this.substring(fileNameIndex + 1)
	
	val fileExtIndex = fileName.lastIndexOf(".")
	val fileShotName = if(fileExtIndex == -1) fileName else fileName.substring(0, fileExtIndex)
	val fileExt = if(fileExtIndex == -1) "" else fileName.substring(fileExtIndex)
	
	return PathInfo(fileDir, fileName, fileShotName, fileExt)
}

/**
 * 路径信息。
 */
class PathInfo(
	/**
	 * 文件所在文件夹。
	 */
	val fileDir: String,
	/**
	 * 文件名。
	 */
	val fileName: String,
	/**
	 * 不包含扩展名在内的文件名。
	 */
	val fileShotName: String,
	/**
	 * 包含"."的文件扩展名。
	 */
	val fileExt: String
) {
	/**
	 * 是否存在上一级文件夹。
	 */
	val hasFileDir = fileDir.isNotEmpty()
	/**
	 * 是否存在文件扩展名。
	 */
	val hasFileExt = fileExt.isNotEmpty()
	
	
	/**
	 * 更改文件所在文件夹为新的文件夹 [newFileDir]。
	 */
	fun changeFileDir(newFileDir: String): String {
		return newFileDir + "\\" + fileName
	}
	
	/**
	 * 更改文件名为新的文件名 [newFileName]。
	 */
	fun changeFileName(newFileName: String): String {
		return fileDir + "\\" + newFileName
	}
	
	/**
	 * 更改不包含扩展名在内的文件名为新的文件名 [newFileShotName]，可指定是否返回全路径 [forFullPath]，默认为true。
	 */
	fun changeFileShotName(newFileShotName: String, forFullPath: Boolean = true): String {
		val newFileName = newFileShotName + fileExt
		return if(forFullPath) fileDir + "\\" + newFileName else newFileName
	}
	
	/**
	 * 更改文件扩展名为新的扩展名 [newFileExt]，可指定是否返回全路径 [forFullPath]，默认为true。
	 */
	fun changeFileExt(newFileExt: String, forFullPath: Boolean = true): String {
		val newFileName = fileShotName + newFileExt
		return if(forFullPath) fileDir + "\\" + newFileName else newFileName
	}
}


/**
 * 得到对应的的地址信息。
 */
fun String.toUrlInfo(): UrlInfo {
	val queryParamIndex = this.lastIndexOf("?")
	val fullPath = when {
		queryParamIndex == -1 -> this
		else -> this.substring(0, queryParamIndex)
	}
	val paramSnippet = when {
		queryParamIndex == -1 -> ""
		else -> this.substring(queryParamIndex + 1)
	}
	
	val queryParamMap = when {
		paramSnippet.isEmpty() -> mapOf()
		else -> paramSnippet.split("&").map { it.split("=") }.groupBy({ it[0] }, { it[1] })
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
	/**
	 * 包含协议、端口等的完整路径。
	 */
	val fullPath: String,
	/**
	 * 查询参数映射。
	 */
	val queryParamMap: Map<String, Any>
) {
	/**
	 * 是否存在查询参数。
	 */
	val hasQueryParam = queryParamMap.isNotEmpty()
}
