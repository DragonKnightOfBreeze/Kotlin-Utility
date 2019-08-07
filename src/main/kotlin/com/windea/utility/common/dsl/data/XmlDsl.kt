@file:Suppress("UNUSED_PARAMETER")

package com.windea.utility.common.dsl.data

import com.windea.utility.common.dsl.*

/**Xml Dsl。*/
class XmlDsl : Dsl {
	val elements: MutableList<XmlDslElement> = mutableListOf()
	
	
	override fun generate(): String {
		return elements.joinToString("\n", "\n") { it.toString() }
	}
}


interface XmlDslElement

/**Xml注释。*/
data class XmlComment(
	var comment: String
) : XmlDslElement {
	override fun toString(): String {
		return "<!--$comment-->\n"
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
		val innerString = text ?: children.joinToString("\n", "\n") { "  $it" }
		
		return "<$name$attributesSnippet>$innerString</$name>\n"
	}
}


/**创建Xml。*/
fun xml(body: XmlDsl.() -> Unit): XmlDsl {
	return XmlDsl().also(body)
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
fun XmlDsl.element(name: String, vararg attributes: Pair<String, Any?>, text: String? = null) {
	this.elements += XmlElement(name, attributes.toMap(), text)
}

/**创建Xml元素。*/
fun XmlDsl.element(name: String, vararg attributes: Pair<String, Any?>, children: XmlElement.() -> Unit) {
	this.elements += XmlElement(name, attributes.toMap()).also(children)
}

/**创建Xml元素。*/
fun XmlElement.element(name: String, vararg attributes: Pair<String, Any?>, text: String? = null) {
	this.children += XmlElement(name, attributes.toMap(), text)
}

/**创建Xml元素。*/
fun XmlElement.element(name: String, vararg attributes: Pair<String, Any?>, children: XmlElement.() -> Unit) {
	this.children += XmlElement(name, attributes.toMap()).also(children)
}
