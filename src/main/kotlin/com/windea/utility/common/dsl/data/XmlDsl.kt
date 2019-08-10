@file:Suppress("UNUSED_PARAMETER")

package com.windea.utility.common.dsl.data

import com.windea.utility.common.dsl.*
import com.windea.utility.common.dsl.data.XmlDslConfig.indent
import com.windea.utility.common.dsl.data.XmlDslConfig.preferAutoClosedTag
import com.windea.utility.common.dsl.data.XmlDslConfig.quote
import com.windea.utility.common.dsl.text.*
import java.lang.annotation.*

/**Xml的领域专用语言。*/
data class XmlDsl(
	override val content: MutableList<XmlDslElement> = mutableListOf()
) : Dsl, XmlDslSuperElement {
	override fun toString(): String {
		return if(content.isEmpty()) "" else content.joinToString("\n", "", "\n")
	}
}

/**Xml领域专用语言的配置。*/
object XmlDslConfig : DslConfig {
	var indentSize: Int = 2
		set(value) {
			field = value.coerceIn(2, 8)
		}
	var preferDoubleQuote: Boolean = true
	var preferAutoClosedTag: Boolean = false
	
	internal val indent get() = " ".repeat(indentSize)
	internal val quote get() = if(preferDoubleQuote) "\"" else "'"
}


/**扩展的Xml功能。*/
@MustBeDocumented
@Inherited
annotation class ExtendedXmlFeature


/**Xml领域专用语言的元素。*/
interface XmlDslElement

/**Xml领域专用语言的父级元素。*/
interface XmlDslSuperElement : XmlDslElement {
	val content: MutableList<XmlDslElement>
	
	operator fun String.unaryPlus() = this@XmlDslSuperElement.text(this)
	
	operator fun String.unaryMinus() = this@XmlDslSuperElement.text(this, true)
	
	operator fun XmlDslElement.plus(text: String) = (+text)
	
	operator fun XmlDslElement.plus(element: StarBoundTextDslElement) = element
}

/**Xml领域专用语言的可换行元素。*/
interface XmlDslNewLineElement : XmlDslElement {
	var newLine: Boolean
}

/**Xml领域专用语言的可以空行分割内容的元素。*/
interface XmlDslBlankLineElement : XmlDslElement {
	var blankLineSize: Int
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
	override var newLine: Boolean = true,
	override var blankLineSize: Int = 0
) : XmlDslSuperElement, XmlDslNewLineElement, XmlDslBlankLineElement {
	override fun toString(): String {
		val attributesSnippet = when {
			attributes.isEmpty() -> ""
			else -> attributes.entries.joinToString(" ", " ") { (k, v) -> "$k=$quote$v$quote" }
		}
		val textSnippet = text?.let { if(newLine) "\n${it.prependIndent(indent)}\n" else text } ?: ""
		val innerTextSnippet = when {
			textSnippet.isEmpty() -> when {
				newLine -> "\n${content.joinToString("\n" + "\n".repeat(blankLineSize)).prependIndent(indent)}\n"
				else -> content.joinToString("")
			}
			textSnippet.isEmpty() && content.isEmpty() -> ""
			else -> textSnippet
		}
		val prefixMarkers = "<$name$attributesSnippet>"
		val suffixMarkers = if(innerTextSnippet.isEmpty() && preferAutoClosedTag) "/>" else "</$name>"
		return "$prefixMarkers$innerTextSnippet$suffixMarkers"
	}
}

/**Xml文本。*/
inline class XmlText(
	val text: String
) : XmlDslElement {
	override fun toString(): String {
		return text
	}
}


/**构建Xml的领域专用语言。*/
fun Dsl.Companion.xml(content: XmlDsl.() -> Unit) = XmlDsl().also { it.content() }

/**配置xml的领域专用语言。*/
fun DslConfig.Companion.xml(config: XmlDslConfig.() -> Unit) = XmlDslConfig.config()


/**创建Xml注释。*/
fun XmlDslSuperElement.comment(comment: String) = XmlComment(comment).also { this.content += it }

/**创建Xml元素。*/
fun XmlDslSuperElement.element(name: String, vararg attributes: Pair<String, Any?>) =
	XmlElement(name, attributes.toMap(), newLine = false).also { this.content += it }

/**创建Xml元素。默认缩进子元素。*/
fun XmlDslSuperElement.element(name: String, vararg attributes: Pair<String, Any?>, content: XmlElement.() -> Unit) =
	XmlElement(name, attributes.toMap()).also { it.content() }.also { this.content += it }

/**创建Xml文本。*/
fun XmlDslSuperElement.text(text: String, clearContent: Boolean = false) =
	XmlText(text).also { if(clearContent) this.content.clear() }.also { this.content += it }


/**配置当前元素的换行。默认换行。*/
fun <T : XmlDslNewLineElement> T.n(newLine: Boolean = true) = this.also { it.newLine = newLine }

/**配置当前元素的内容间空行数量。默认为1。*/
fun <T : XmlDslBlankLineElement> T.bn(blankLineSize: Int = 1) = this.also { it.blankLineSize = blankLineSize }
