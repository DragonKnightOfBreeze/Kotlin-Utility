package com.windea.commons.kotlin.domain

import com.windea.commons.kotlin.extension.*
import java.io.*

/**Markdown对象。*/
class Markdown(
	/**Markdown文本。*/
	var text: String,
	/**是否启用扩展语法。*/
	var enableExtendedSyntax: Boolean = false,
	/**是否启用Critical Markup语法。*/
	var enableCriticalMarkup: Boolean = false
) {
	/**导入使用特殊语法`@import("...")`注明的文本文件的文本，或者图片文件的图片地址，或者其他类型文件的地址。*/
	fun doImport() {
		if(!enableExtendedSyntax) {
			throw IllegalStateException("Extended Syntax is not enabled.")
		}
		text = text.lines().crossLine { lines ->
			lines.map { line ->
				if(line.crossLineSurroundsWith("```")) {
					line
				} else {
					line.substring("@import(.*?)".toRegex()).map { params ->
						val filePath = params.substringBefore(",").trim().unquote()
						val options = params.substringBefore(",").split(",").map { it.trim() }
						//TODO 适用一些特定导入选项
						val file = File(filePath)
						if(file.exists()) {
							when {
								file.isTextFile -> file.readText()
								file.isImageFile -> "![${file.name}](${file.path})"
								else -> "[${file.name}](${file.path})"
							}
						} else {
							"[WARN] File \"${file.name}\" does not exist."
						}
					}
				}
			}.joinToString("\n")
		}
	}
	
	/**删除以Markdown语法标记的删除文本。*/
	fun doDelete() {
		text = text.remove("~~.*?~~".toRegex())
	}
	
	/**替换以CriticalMarkdown语法标记的删除文本。*/
	fun doCriticalMarkupDelete() {
		if(!enableCriticalMarkup) {
			throw IllegalStateException("Critical Markup Syntax is not enabled.")
		}
		text = text.remove("\\{--.*?--}".toRegex())
	}
	
	/**替换以CriticalMarkdown语法标记的替换文本。*/
	fun doCriticalMarkupReplace() {
		if(!enableCriticalMarkup) {
			throw IllegalStateException("Critical Markup Syntax is not enabled.")
		}
		text = text.replace("\\{~~(.*?)~>(.*?)~~}".toRegex(), "$2")
	}
	
	/**转化为html文本。*/
	fun toHtml(indent: Int = 2): String {
		val fixedIndent = indent.coerceIn(2..8)
		TODO()
	}
}
