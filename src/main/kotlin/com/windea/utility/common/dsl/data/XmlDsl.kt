@file:Suppress("UNUSED_PARAMETER")

package com.windea.utility.common.dsl.data

import com.windea.utility.common.dsl.*
import com.windea.utility.common.dsl.data.XmlDslConfig.indent
import com.windea.utility.common.dsl.data.XmlDslConfig.quote
import com.windea.utility.common.dsl.text.*

/**Xml的领域专用语言。*/
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
	
	operator fun String.unaryPlus() = this@XmlDslSuperElement.text(this)
	
	operator fun XmlDslElement.plus(text: String) = (+text)
	
	operator fun XmlDslElement.plus(element: StarBoundTextDslElement) = element
}

/**Xml领域专用语言的可换行元素。*/
interface XmlDslNewLineElement : XmlDslElement {
	var newLine: Boolean
}


/**Xml注释。*/
data class XmlComment(
	val text: String,
	override var newLine: Boolean = false
) : XmlDslNewLineElement {
	override fun toString(): String {
		val textSnippet = if(newLine) "\n${text.prependIndent(indent)}\n" else text
		return "<!--$textSnippet-->"
	}
}

/**Xml元素。*/
data class XmlElement(
	val name: String,
	val attributes: Map<String, Any?>,
	val text: String? = null,
	override val content: MutableList<XmlDslElement> = mutableListOf(),
	override var newLine: Boolean = false
) : XmlDslSuperElement, XmlDslNewLineElement {
	override fun toString(): String {
		val attributesSnippet = when {
			attributes.isEmpty() -> ""
			else -> attributes.entries.joinToString(" ", " ") { (k, v) -> "$k=$quote$v$quote" }
		}
		val textSnippet = text?.let { if(newLine) "\n${it.prependIndent(indent)}\n" else text } ?: ""
		val innerTextSnippet = when {
			textSnippet.isEmpty() -> when {
				newLine -> "\n${content.joinToString("\n").prependIndent(indent)}\n"
				else -> content.joinToString("")
			}
			textSnippet.isEmpty() && content.isEmpty() -> ""
			else -> textSnippet
		}
		return "<$name$attributesSnippet>$innerTextSnippet</$name>"
	}
}

/**Xml文本。*/
data class XmlText(
	val text: String
) : XmlDslElement {
	override fun toString(): String {
		return text
	}
}


/**构建Xml的领域专用语言。*/
fun Dsl.Companion.xml(content: XmlDsl.() -> Unit): XmlDsl {
	return XmlDsl().also { it.content() }
}

/**配置xml的领域专用语言。*/
fun DslConfig.Companion.xml(config: XmlDslConfig.() -> Unit) {
	XmlDslConfig.config()
}


/**创建Xml注释。*/
fun XmlDslSuperElement.comment(comment: String): XmlComment {
	return XmlComment(comment).also { this.content += it }
}

/**创建Xml元素。*/
fun XmlDslSuperElement.element(name: String, vararg attributes: Pair<String, Any?>): XmlElement {
	return XmlElement(name, attributes.toMap()).also { this.content += it }
}

/**创建Xml元素。默认缩进子元素。*/
fun XmlDslSuperElement.element(name: String, vararg attributes: Pair<String, Any?>, content: XmlElement.() -> Unit): XmlElement {
	return XmlElement(name, attributes.toMap(), newLine = true).also { it.content() }.also { this.content += it }
}

/**创建Xml文本。*/
fun XmlDslSuperElement.text(text: String): XmlText {
	return XmlText(text).also { this.content += it }
}

/**对可换行元素进行换行。例如，对注释文本、标签的子标签进行换行。*/
fun <T : XmlDslNewLineElement> T.n(): T {
	return this.also { it.newLine = true }
}

/**对可换行元素取消换行。例如，对注释文本、标签的子标签取消换行。*/
fun <T : XmlDslNewLineElement> T.un(): T {
	return this.also { it.newLine = false }
}
