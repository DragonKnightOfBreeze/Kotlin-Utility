package com.windea.utility.common.dsl.text

import com.windea.utility.common.annotations.marks.*
import com.windea.utility.common.dsl.*
import com.windea.utility.common.dsl.text.MarkdownDslConfig.addTrailingMarkers
import com.windea.utility.common.dsl.text.MarkdownDslConfig.spaceAfterMarkers

//TODO
//TODO 关联Html标签

@NotTested
/**Markdown的领域专用语言。*/
class MarkdownDsl : Dsl {
	val builder: StringBuilder = StringBuilder()
	val elements: MutableList<MarkdownDslElement> = mutableListOf()
	
	override fun generate(): String {
		return elements.joinTo(builder, "\n", "\n", "\n") { it.toString() }.toString()
	}
}

object MarkdownDslConfig : DslConfig {
	var spaceAfterMarkers: Boolean = true
	var addTrailingMarkers: Boolean = false
}


interface MarkdownDslElement

interface InlineMarkdownDslElement

interface BlockMarkdownDslElement

interface ExtendedMarkdownDslElement

abstract class MarkdownHeader(
	open val text: String,
	val headerLevel: Int
) : InlineMarkdownDslElement {
	override fun toString(): String {
		val markers = "#".repeat(headerLevel)
		val space = if(spaceAfterMarkers) " " else ""
		val trailingMarkers = if(addTrailingMarkers) markers else ""
		return "$markers$space$text$trailingMarkers"
	}
}

data class MarkdownHeader1(
	override val text: String
) : MarkdownHeader(text, 1)

data class MarkdownHeader2(
	override val text: String
) : MarkdownHeader(text, 2)

data class MarkdownHeader3(
	override val text: String
) : MarkdownHeader(text, 3)

data class MarkdownHeader4(
	override val text: String
) : MarkdownHeader(text, 4)

data class MarkdownHeader5(
	override val text: String
) : MarkdownHeader(text, 5)

data class MarkdownHeader6(
	override val text: String
) : MarkdownHeader(text, 6)

abstract class MarkdownRichText(
	open val text: String,
	val prefixMarkers: String,
	val suffixMarkers: String
) : InlineMarkdownDslElement {
	override fun toString(): String {
		return "$prefixMarkers$text$suffixMarkers"
	}
}

data class MarkdownBoldText(
	override val text: String
) : MarkdownRichText(text, "**", "**")

data class MarkdownItalicText(
	override val text: String
) : MarkdownRichText(text, "*", "*")

data class MarkdownStrikeLineText(
	override val text: String
) : MarkdownRichText(text, "~~", "~~")

data class MarkdownUnderLineText(
	override val text: String
) : MarkdownRichText(text, "++", "++")

data class MarkdownHighlightText(
	override val text: String
) : MarkdownRichText(text, "==", "=="), ExtendedMarkdownDslElement

data class MarkdownSuperText(
	override val text: String
) : MarkdownRichText(text, "^", "^"), ExtendedMarkdownDslElement

data class MarkdownSubText(
	override val text: String
) : MarkdownRichText(text, "~", "~"), ExtendedMarkdownDslElement
