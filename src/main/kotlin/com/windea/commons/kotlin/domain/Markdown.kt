package com.windea.commons.kotlin.domain

import com.windea.commons.kotlin.extension.*
import java.io.*

/**Markdown对象。*/
class Markdown(
	var text: String,
	val useExtendedSyntax: Boolean = false,
	val userCriticalMarkup: Boolean = false
) {
	/**导入使用特殊语法`@import("...")`注明的文本文件的文本，或者图片文件的图片地址，或者其他类型文件的地址。*/
	fun import() {
		val importOptions = text.substring("@import(.*?)".toRegex())
		for(importOption in importOptions) {
			val filePath = importOption.trim().unquote()
			File(filePath).also {
				if(it.exists() && it.isTextFile()) {
					TODO()
				}
			}
		}
	}
	
	/**删除以Markdown语法标记的删除文本。*/
	fun doDelete() {
		text = text.remove("~~.*?~~".toRegex())
	}
	
	/**替换以CriticalMarkdown语法标记的删除文本。*/
	fun doCriticalMarkupDelete() {
		if(!userCriticalMarkup) println("Critical Markup Syntax is not enabled.")
		text = text.remove("\\{--.*?--}".toRegex())
	}
	
	/**替换以CriticalMarkdown语法标记的替换文本。*/
	fun doCriticalMarkupReplace() {
		if(!userCriticalMarkup) println("Critical Markup Syntax is not enabled.")
		text = text.replace("\\{~~(.*?)~>(.*?)~~}".toRegex(), "$2")
	}
	
	/**转化成html文本。*/
	fun toHtml(indent: Int = 2): String {
		val fixedIndent = indent.coerceIn(2..8)
		TODO()
	}
}
