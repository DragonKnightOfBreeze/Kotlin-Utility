@file:Suppress("UNUSED_PARAMETER")

package com.windea.utility.common.dsl.data

import com.windea.utility.common.annotations.marks.*
import com.windea.utility.common.dsl.*
import com.windea.utility.common.dsl.data.XmlDslConfig.indent

/**Xml的领域专用语言。*/
@NotTested
@NotSuitable("需要精确控制生成文本的换行时")
class XmlDsl : Dsl {
	val builder: StringBuilder = StringBuilder()
	val elements: MutableList<XmlDslElement> = mutableListOf()
	
	override fun generate(): String {
		return elements.joinTo(builder, "\n", "\n", "\n") { it.toString() }.toString()
	}
}

object XmlDslConfig : DslConfig {
	var indent: String = "  "
}


/**Xml领域专用语言的元素。*/
interface XmlDslElement

/**Xml注释。*/
data class XmlComment(
	var comment: String
) : XmlDslElement {
	override fun toString(): String {
		return "<!--$comment-->"
	}
}

/**Xml元素。*/
data class XmlElement(
	var name: String,
	val attributes: Map<String, Any?>,
	var text: String? = null
) : XmlDslElement {
	val children: MutableList<XmlDslElement> = mutableListOf()
	
	override fun toString(): String {
		val attributesSnippet = when {
			attributes.isEmpty() -> ""
			else -> attributes.entries.joinToString(" ", " ") { (k, v) -> "$k=\"$v\"" }
		}
		val innerString = text ?: children.joinToString("\n", "\n", "\n") { "$$indent$it" }
		return "<$name$attributesSnippet>$innerString</$name>"
	}
}


/**构建Xml领域专用语言。*/
fun Dsl.Companion.xml(body: XmlDsl.() -> Unit): XmlDsl {
	return XmlDsl().apply(body)
}

/**配置xml领域专用语言。*/
fun DslConfig.Companion.xml(config: XmlDslConfig.() -> Unit) {
	XmlDslConfig.config()
}


/**创建Xml注释。*/
fun XmlDsl.comment(comment: String) {
	this.elements += XmlComment(comment)
}

/**创建Xml注释。*/
fun XmlElement.comment(comment: String) {
	this.children += XmlComment(comment)
}

/**创建Xml元素。*/
//TODO 将text属性作为一个返回字符串的方法参数，而非一个字符串参数
fun XmlDsl.element(name: String, vararg attributes: Pair<String, Any?>, text: String? = null) {
	this.elements += XmlElement(name, attributes.toMap(), text)
}

/**创建Xml元素。*/
fun XmlDsl.element(name: String, vararg attributes: Pair<String, Any?>, children: XmlElement.() -> Unit) {
	this.elements += XmlElement(name, attributes.toMap()).apply(children)
}

/**创建Xml元素。*/
fun XmlElement.element(name: String, vararg attributes: Pair<String, Any?>, text: String? = null) {
	this.children += XmlElement(name, attributes.toMap(), text)
}

/**创建Xml元素。*/
fun XmlElement.element(name: String, vararg attributes: Pair<String, Any?>, children: XmlElement.() -> Unit) {
	this.children += XmlElement(name, attributes.toMap()).apply(children)
}
