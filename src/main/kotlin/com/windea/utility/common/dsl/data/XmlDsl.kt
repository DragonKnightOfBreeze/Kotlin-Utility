@file:Suppress("UNUSED_PARAMETER")

package com.windea.utility.common.dsl.data

import com.windea.utility.common.annotations.marks.*
import com.windea.utility.common.dsl.*
import com.windea.utility.common.dsl.data.XmlDslConfig.indent
import com.windea.utility.common.dsl.data.XmlDslConfig.quote

/**Xml的领域专用语言。*/
@NotTested
@NotSuitable("需要精确控制生成文本的换行时")
data class XmlDsl(
	override val content: MutableList<XmlDslElement> = mutableListOf()
) : Dsl, XmlDslSuperElement {
	override fun toString(): String {
		return if(content.isEmpty()) "" else content.joinToString("\n", "", "\n") { it.toString() }
	}
}

/**Xml领域专用语言的配置。*/
object XmlDslConfig : DslConfig {
	var indentSize: Int = 2
		set(value) {
			field = value.coerceIn(2, 8)
		}
	var preferDoubleQuote: Boolean = true
	
	internal val indent get() = " ".repeat(indentSize)
	internal val quote get() = if(preferDoubleQuote) "\"" else "'"
}


/**Xml领域专用语言的元素。*/
interface XmlDslElement

/**Xml领域专用语言的父级元素。*/
interface XmlDslSuperElement : XmlDslElement {
	val content: MutableList<XmlDslElement>
}

/**Xml领域专用语言的可换行元素。*/
interface XmlDslNewLineElement : XmlDslElement {
	val newLine: Boolean
}

/**Xml注释。*/
data class XmlComment(
	var comment: String
) : XmlDslNewLineElement {
	override val newLine: Boolean = false
	
	override fun toString(): String {
		val n = if(newLine) "\n" else ""
		return "<!--$n$comment$n-->"
	}
}

/**Xml元素。*/
data class XmlElement(
	var name: String,
	val attributes: Map<String, Any?>,
	var text: String? = null,
	override val content: MutableList<XmlDslElement> = mutableListOf()
) : XmlDslSuperElement, XmlDslNewLineElement {
	override val newLine: Boolean = true
	
	override fun toString(): String {
		val attributesSnippet = when {
			attributes.isEmpty() -> ""
			else -> attributes.entries.joinToString(" ", " ") { (k, v) -> "$k=$quote$v$quote" }
		}
		val n = if(newLine) "\n" else ""
		val innerText = text ?: (if(content.isEmpty()) "" else content.joinToString("\n", n, n) { "$indent$it" })
		return "<$name$attributesSnippet>$innerText</$name>"
	}
}


/**构建Xml的领域专用语言。*/
fun Dsl.Companion.xml(content: XmlDsl.() -> Unit): XmlDsl {
	return XmlDsl().also { it.content() }
}

/**配置xml。*/
fun DslConfig.Companion.xml(config: XmlDslConfig.() -> Unit) {
	XmlDslConfig.config()
}


/**创建Xml注释。*/
fun XmlDslSuperElement.comment(comment: String): XmlComment {
	return XmlComment(comment).also { this.content += it }
}


//TODO 将text属性作为一个返回字符串的方法参数，而非一个字符串参数
/**创建Xml元素。*/
fun XmlDsl.element(name: String, vararg attributes: Pair<String, Any?>, text: String? = null): XmlElement {
	return XmlElement(name, attributes.toMap(), text).also { this.content += it }
}

/**创建Xml元素。*/
fun XmlDslSuperElement.element(name: String, vararg attributes: Pair<String, Any?>, content: XmlElement.() -> Unit): XmlElement {
	return XmlElement(name, attributes.toMap()).also { it.content() }.also { this.content += it }
}

