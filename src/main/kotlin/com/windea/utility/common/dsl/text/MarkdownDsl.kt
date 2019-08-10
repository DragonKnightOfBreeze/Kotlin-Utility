package com.windea.utility.common.dsl.text

import com.windea.utility.common.annotations.marks.*
import com.windea.utility.common.dsl.*
import com.windea.utility.common.dsl.data.*
import com.windea.utility.common.dsl.text.MarkdownDslConfig.addTrailingBreakSpaces
import com.windea.utility.common.dsl.text.MarkdownDslConfig.addTrailingHeaderMarkers
import com.windea.utility.common.dsl.text.MarkdownDslConfig.indent
import com.windea.utility.common.dsl.text.MarkdownDslConfig.indentSize
import com.windea.utility.common.dsl.text.MarkdownDslConfig.initialMarker
import com.windea.utility.common.dsl.text.MarkdownDslConfig.listIndentSize
import com.windea.utility.common.dsl.text.MarkdownDslConfig.quote
import com.windea.utility.common.dsl.text.MarkdownDslConfig.quoteIndentSize
import com.windea.utility.common.dsl.text.MarkdownDslConfig.repeatableMarkerSize
import com.windea.utility.common.dsl.text.MarkdownDslConfig.truncated
import com.windea.utility.common.extensions.*
import java.lang.annotation.*

//TODO 关联Html标签
//TODO 确保text只能是textBlock的子元素

@NotTested
/**Markdown的领域专用语言。*/
data class MarkdownDsl(
	override val content: MutableList<MarkdownDslElement> = mutableListOf()
) : Dsl, MarkdownDslBlockElement, MarkdownDslSuperElement<MarkdownDslElement>, MarkdownDslInlineSuperElement {
	fun generateToc() {
		TODO()
	}
	
	fun performImport() {
		TODO()
	}
	
	fun performMacros() {
		TODO()
	}
	
	override fun toString(): String {
		val contentSnippet = content.joinToString("\n\n")
		//TODO 未测试
		val trailingSnippet = content.deepFlatMap().values
			.filterIsInstance<MarkdownDslReferenceElement>().distinctBy { it.reference }
			.joinToString("\n") { it.toTrailingString() }.ifNotEmpty { "\n\n\n$it" }
		return "$contentSnippet$trailingSnippet"
	}
}

/**Markdown领域专用语言的配置。*/
object MarkdownDslConfig : DslConfig {
	var indentSize: Int = 4
		set(value) {
			field = value.coerceIn(2, 8)
		}
	var listIndentSize: Int = 4
		set(value) {
			field = value.coerceIn(2, 8)
		}
	var quoteIndentSize: Int = 2
		set(value) {
			field = value.coerceIn(2, 8)
		}
	var codeIndentSize: Int = 2
		set(value) {
			field = value.coerceIn(2, 8)
		}
	var repeatableMarkerSize: Int = 6
		set(value) {
			field = value.coerceIn(3, 6)
		}
	var truncated = "..."
	var preferDoubleQuote: Boolean = true
	var preferAsteriskInitialMaker: Boolean = true
	var preferUpperCompleteMarker: Boolean = true
	var addTrailingHeaderMarkers: Boolean = false
	var addTrailingBreakSpaces: Boolean = true
	
	
	internal val indent get() = " ".repeat(indentSize)
	internal val listIndent get() = " ".repeat(listIndentSize)
	internal val quoteIndent get() = " ".repeat(quoteIndentSize)
	internal val codeIndent get() = " ".repeat(codeIndentSize)
	internal val quote get() = if(preferDoubleQuote) "\"" else "'"
	internal val initialMarker get() = if(preferAsteriskInitialMaker) "*" else "-"
	internal val completeMarker get() = if(preferUpperCompleteMarker) "X" else "x"
	
	
	internal fun String.addTrailingHeaderMarkers(headerLevel: Int): String {
		return if(addTrailingHeaderMarkers) "$this ${"#".repeat(headerLevel)}" else this
	}
	
	internal fun String.addTrailingBreakSpaces(): String {
		//为了性能考虑，不将字符串细分为每一行，然后只为非空行添加尾随空格。使用正则表达式。
		return if(addTrailingBreakSpaces) this.replace("\n([^\n])".toRegex(), "  \n$1") else this
	}
}


/**扩展的Markdown功能。*/
@MustBeDocumented
@Inherited
annotation class ExtendedMarkdownFeature


/**Markdown领域专用语言的元素。*/
interface MarkdownDslElement

/**Markdown领域专用语言的单行元素。*/
interface MarkdownDslLineElement : MarkdownDslElement

/**Markdown领域专用语言的行内元素。*/
interface MarkdownDslInlineElement : MarkdownDslElement

/**Markdown领域专用语言的引用元素。*/
interface MarkdownDslReferenceElement : MarkdownDslLineElement, MarkdownDslInlineElement {
	val reference: String
	
	fun toTrailingString(): String
}

/**Markdown领域专用语言的块元素。*/
interface MarkdownDslBlockElement : MarkdownDslElement

/**Markdown领域专用语言的父级元素。*/
interface MarkdownDslSuperElement<T : MarkdownDslElement> : MarkdownDslElement {
	val content: MutableList<in T>
	
	operator fun MarkdownDslElement.plus(element: MarkdownDslElement) = element
}

/**Markdown领域专用语言的行内父级元素。*/
interface MarkdownDslInlineSuperElement : MarkdownDslElement {
	val content: MutableList<in MarkdownDslInlineElement>
	
	operator fun String.unaryPlus() = this@MarkdownDslInlineSuperElement.text(this)
	
	operator fun String.unaryMinus() = this@MarkdownDslInlineSuperElement.text(this, true)
	
	operator fun XmlDslElement.plus(text: String) = (+text)
}

/**Markdown领域专用语言的可以空行分割内容的元素。*/
interface MarkdownDslBlankLineElement : MarkdownDslElement {
	var blankLineSize: Int
}


/**Markdown文本。*/
inline class MarkdownText(
	val text: String
) : MarkdownDslInlineElement {
	override fun toString(): String {
		return text.addTrailingBreakSpaces()
	}
}

/**Markdown文本块。*/
data class MarkdownTextBlock(
	override val content: MutableList<MarkdownDslInlineElement> = mutableListOf()
) : MarkdownDslBlockElement, MarkdownDslSuperElement<MarkdownDslInlineElement>, MarkdownDslInlineSuperElement {
	override fun toString(): String {
		return content.joinToString().addTrailingBreakSpaces()
	}
}


/**Markdown标题。*/
abstract class MarkdownHeader(
	open val headerLevel: Int,
	override val content: MutableList<MarkdownDslInlineElement>
) : MarkdownDslLineElement, MarkdownDslSuperElement<MarkdownDslInlineElement>, MarkdownDslInlineSuperElement

/**Setext风格的Markdown标题。*/
abstract class MarkdownSetextHeader(
	override val headerLevel: Int,
	override val content: MutableList<MarkdownDslInlineElement>
) : MarkdownHeader(headerLevel, content) {
	override fun toString(): String {
		val contentSnippet = content.joinToString("")
		val suffixMarker = if(headerLevel == 1) "=" else "-"
		val suffixMarkers = suffixMarker.repeat(repeatableMarkerSize)
		return "$contentSnippet\n$suffixMarkers"
	}
}

/**Atx风格的Markdown标题。*/
abstract class MarkdownAtxHeader(
	override val headerLevel: Int,
	override val content: MutableList<MarkdownDslInlineElement>
) : MarkdownHeader(headerLevel, content) {
	override fun toString(): String {
		val prefixMarkers = "#".repeat(headerLevel) + " "
		val contentSnippet = content.joinToString("")
		return "$prefixMarkers$contentSnippet".addTrailingHeaderMarkers(headerLevel)
	}
}

/**Markdown主标题。*/
data class MarkdownMainHeader(
	override val content: MutableList<MarkdownDslInlineElement> = mutableListOf()
) : MarkdownSetextHeader(1, content)

/**Markdown副标题。*/
data class MarkdownSubHeader(
	override val content: MutableList<MarkdownDslInlineElement> = mutableListOf()
) : MarkdownSetextHeader(2, content)

/**Markdown一级标题。*/
data class MarkdownHeader1(
	override val content: MutableList<MarkdownDslInlineElement> = mutableListOf()
) : MarkdownSetextHeader(1, content)

/**Markdown二级标题。*/
data class MarkdownHeader2(
	override val content: MutableList<MarkdownDslInlineElement> = mutableListOf()
) : MarkdownSetextHeader(2, content)

/**Markdown三级标题。*/
data class MarkdownHeader3(
	override val content: MutableList<MarkdownDslInlineElement> = mutableListOf()
) : MarkdownSetextHeader(3, content)

/**Markdown四级标题。*/
data class MarkdownHeader4(
	override val content: MutableList<MarkdownDslInlineElement> = mutableListOf()
) : MarkdownSetextHeader(4, content)

/**Markdown五级标题。*/
data class MarkdownHeader5(
	override val content: MutableList<MarkdownDslInlineElement> = mutableListOf()
) : MarkdownSetextHeader(5, content)

/**Markdown六级标题。*/
data class MarkdownHeader6(
	override val content: MutableList<MarkdownDslInlineElement> = mutableListOf()
) : MarkdownSetextHeader(6, content)

/**Markdown水平分割线。*/
class MarkdownHorizontalLine : MarkdownDslLineElement {
	override fun toString(): String {
		return initialMarker * repeatableMarkerSize
	}
}


/**Markdown富文本。富文本中的嵌套富文本视为普通文本。*/
abstract class MarkdownRichText(
	open val text: String,
	protected open val prefixMarkers: String,
	protected open val suffixMarkers: String
) : MarkdownDslInlineElement {
	override fun toString(): String {
		return "$prefixMarkers$text$suffixMarkers"
	}
}

/**Markdown加粗文本。*/
data class MarkdownBoldText(
	override val text: String
) : MarkdownRichText(text, "**", "**")

/**Markdown斜体文本。*/
data class MarkdownItalicText(
	override val text: String
) : MarkdownRichText(text, "*", "*")

/**Markdown删除文本。*/
data class MarkdownStrikeLineText(
	override val text: String
) : MarkdownRichText(text, "~~", "~~")

/**Markdown下划线文本。*/
@ExtendedMarkdownFeature
data class MarkdownUnderLineText(
	override val text: String
) : MarkdownRichText(text, "++", "++")

/**Markdown高亮文本。*/
@ExtendedMarkdownFeature
data class MarkdownHighlightText(
	override val text: String
) : MarkdownRichText(text, "==", "==")

/**Markdown上标文本。*/
@ExtendedMarkdownFeature
data class MarkdownSuperscriptText(
	override val text: String
) : MarkdownRichText(text, "^", "^")

/**Markdown下标文本。*/
@ExtendedMarkdownFeature
data class MarkdownSubscriptText(
	override val text: String
) : MarkdownRichText(text, "~", "~")


/**CriticMarkup文本。*/
@ExtendedMarkdownFeature
abstract class CriticMarkupText(
	override val text: String,
	override val prefixMarkers: String,
	override val suffixMarkers: String
) : MarkdownRichText(text, prefixMarkers, suffixMarkers) {
	override fun toString(): String {
		return "$prefixMarkers $text $suffixMarkers"
	}
}

/**CriticMarkup添加文本。*/
data class CriticMarkupAppendText(
	override val text: String
) : CriticMarkupText(text, "{++", "++}")

/**CriticMarkup删除文本。*/
data class CriticMarkupDeleteText(
	override val text: String
) : CriticMarkupText(text, "{--", "--}")

/**CriticMarkup替换文本。*/
data class CriticMarkupReplaceText(
	override val text: String,
	val replacedText: String,
	private val infixMarkers: String = "~>"
) : CriticMarkupText(text, "{~~", "~~}") {
	override fun toString(): String {
		return "$prefixMarkers $text $infixMarkers $replacedText $suffixMarkers"
	}
}

/**CriticMarkup注释文本。*/
data class CriticMarkupCommentText(
	override val text: String
) : CriticMarkupText(text, "{>>", "<<}")

/**CriticMarkup高亮文本。*/
data class CriticMarkupHighlightText(
	override val text: String
) : CriticMarkupText(text, "{==", "==}")


/**Markdown链接。*/
abstract class MarkdownLink(
	open val url: String
) : MarkdownDslElement

/**Markdown自动链接。*/
@ExtendedMarkdownFeature
data class MarkdownAutoLink(
	override val url: String
) : MarkdownLink(url), MarkdownDslInlineElement {
	override fun toString(): String {
		return "<$url>"
	}
}

/**Markdown行内链接。*/
data class MarkdownInlineLink(
	val text: String,
	override val url: String,
	val title: String? = null
) : MarkdownLink(url), MarkdownDslInlineElement {
	override fun toString(): String {
		val titleSnippet = title?.let { " $quote$title$quote" } ?: ""
		return "[$text]($url$titleSnippet)"
	}
}

/**Markdown行内图片链接。*/
class MarkdownInlineImageLink(
	val text: String,
	override val url: String,
	val title: String? = null
) : MarkdownLink(url), MarkdownDslInlineElement {
	override fun toString(): String {
		val titleSnippet = title?.let { " $quote$title$quote" } ?: ""
		return "![$text]($url$titleSnippet)"
	}
}

/**Markdown引用链接。*/
data class MarkdownReferenceLink(
	val text: String? = null,
	override val reference: String,
	override val url: String = truncated,
	val title: String? = null
) : MarkdownLink(url), MarkdownDslReferenceElement {
	override fun toString(): String {
		val textSnippet = text?.let { "[$text]" } ?: ""
		return "$textSnippet[$reference]"
	}
	
	override fun toTrailingString(): String {
		val titleSnippet = title?.let { " $quote$title$quote" } ?: ""
		return "[$reference]: $url$titleSnippet"
	}
}

/**Markdown引用图片链接。*/
data class MarkdownReferenceImageLink(
	val text: String? = null,
	override val reference: String,
	override val url: String = truncated,
	val title: String? = null
) : MarkdownLink(url), MarkdownDslReferenceElement {
	override fun toString(): String {
		val textSnippet = text?.let { "[$text]" } ?: ""
		return " !$textSnippet[$reference] "
	}
	
	override fun toTrailingString(): String {
		val titleSnippet = title?.let { " $quote$title$quote" } ?: ""
		return "[$reference]: $url$titleSnippet"
	}
}

/**Markdown维基链接。采用Github风格，标题在前，地址在后。*/
@ExtendedMarkdownFeature
data class MarkdownWikiLink(
	val text: String,
	override val url: String
) : MarkdownLink(url), MarkdownDslInlineElement {
	override fun toString(): String {
		return "[[$text|$url]]"
	}
}


/**Markdown图标。*/
@ExtendedMarkdownFeature
data class MarkdownIcon(
	val name: String
) : MarkdownDslInlineElement {
	override fun toString(): String {
		return ":$name:"
	}
}

/**Markdown下标。*/
@ExtendedMarkdownFeature
data class MarkdownFootNote(
	override val reference: String,
	val text: String = truncated
) : MarkdownDslReferenceElement {
	override fun toString(): String {
		return "[^$reference]"
	}
	
	override fun toTrailingString(): String {
		return "[^$reference]: $text"
	}
}

/**Markdown缩写。*/
@ExtendedMarkdownFeature
data class MarkdownAbbreviation(
	override val reference: String,
	val text: String = truncated
) : MarkdownDslReferenceElement {
	override fun toString(): String {
		return " $reference "
	}
	
	override fun toTrailingString(): String {
		return "*[$reference]: $text"
	}
}


/**Markdown列表。*/
data class MarkdownList(
	override val content: MutableList<MarkdownListNode> = mutableListOf(),
	override var blankLineSize: Int = 0
) : MarkdownDslBlockElement, MarkdownDslSuperElement<MarkdownListNode>, MarkdownDslBlankLineElement {
	override fun toString(): String {
		return when {
			content.isEmpty() -> ""
			else -> content.joinToString("\n" + "\n".repeat(blankLineSize))
		}
	}
}

/**Markdown列表节点。*/
abstract class MarkdownListNode(
	val prefixMarker: String,
	override val content: MutableList<MarkdownDslInlineElement>,
	open val list: MarkdownList
) : MarkdownDslBlockElement, MarkdownDslSuperElement<MarkdownDslInlineElement>, MarkdownDslInlineSuperElement {
	override fun toString(): String {
		val contentSnippet = when {
			content.isEmpty() -> prefixMarker + " ".repeat(listIndentSize - 1) + truncated
			else -> prefixMarker + content.joinToString("\n").prependIndent(indent).drop(1)
		}
		val listSnippet = when {
			list.content.isEmpty() -> ""
			else -> "\n" + list.toString().prependIndent(indent)
		}
		return "$contentSnippet$listSnippet"
	}
}

/**Markdown有序列表节点。*/
data class MarkdownOrderedListNode(
	val order: String,
	override val content: MutableList<MarkdownDslInlineElement> = mutableListOf(),
	override val list: MarkdownList = MarkdownList()
) : MarkdownListNode(order, content, list)

/**Markdown无序列表节点。*/
data class MarkdownUnorderedListNode(
	override val content: MutableList<MarkdownDslInlineElement> = mutableListOf(),
	override val list: MarkdownList = MarkdownList()
) : MarkdownListNode(initialMarker, content, list)

/**Markdown任务列表节点。*/
data class MarkdownTaskListNode(
	val completeStatus: Boolean,
	override val content: MutableList<MarkdownDslInlineElement> = mutableListOf(),
	override val list: MarkdownList = MarkdownList(),
	val completeMarkers: String = "$initialMarker [${if(completeStatus) MarkdownDslConfig.completeMarker else " "}]"
) : MarkdownListNode(completeMarkers, content, list)


/**Markdown定义列表。*/
@ExtendedMarkdownFeature
data class MarkdownDefinitionList(
	val title: String,
	override val content: MutableList<MarkdownDefinitionListNode> = mutableListOf(),
	override var blankLineSize: Int = 0
) : MarkdownDslBlockElement, MarkdownDslSuperElement<MarkdownDefinitionListNode>, MarkdownDslBlankLineElement {
	override fun toString(): String {
		val titleSnippet = if(title.isEmpty()) truncated else title
		val contentSnippet = when {
			content.isEmpty() -> ":" + " ".repeat(indentSize - 1) + truncated
			else -> content.joinToString("\n" + "\n".repeat(blankLineSize))
		}
		return "$titleSnippet\n$contentSnippet"
	}
}

/**Markdown定义列表节点。*/
@ExtendedMarkdownFeature
data class MarkdownDefinitionListNode(
	override val content: MutableList<MarkdownDslInlineElement> = mutableListOf()
) : MarkdownDslBlockElement, MarkdownDslSuperElement<MarkdownDslInlineElement>, MarkdownDslInlineSuperElement {
	override fun toString(): String {
		return when {
			content.isEmpty() -> ":" + " ".repeat(indentSize - 1) + truncated
			else -> ":" + content.joinToString("\n").prependIndent(indent).drop(1)
		}
	}
}


/**Markdown表格。*/
data class MarkdownTable(
	var header: MarkdownTableHeaderRow = MarkdownTableHeaderRow(),
	override val content: MutableList<MarkdownTableBodyRow> = mutableListOf()
) : MarkdownDslBlockElement, MarkdownDslSuperElement<MarkdownTableBodyRow> {
	override fun toString(): String {
		require(content.isNotEmpty()) { "Table row size must be greater than 0." }
		
		val contentSnippet = content.joinToString("\n")
		return "$header\n$contentSnippet"
	}
}

/**Markdown表格的行。*/
abstract class MarkdownTableRow(
	override val content: MutableList<MarkdownTableColumn> = mutableListOf()
) : MarkdownDslLineElement, MarkdownDslSuperElement<MarkdownTableColumn>

/**Markdown表格头部的行。*/
data class MarkdownTableHeaderRow(
	override val content: MutableList<MarkdownTableColumn> = mutableListOf()
) : MarkdownTableRow(content) {
	override fun toString(): String {
		require(content.isNotEmpty()) { "Table header column size must be greater than 0." }
		
		val contentStringList = content.map { it.toString() }
		val contentSnippet = contentStringList.joinToString(" | ", "| ", " |")
		val delimiterLine = contentStringList.joinToString("-|-", "|-", "-|") { "-".repeat(it.count()) }
		return "$contentSnippet\n$delimiterLine"
	}
}

/**Markdown表格主体的行。*/
data class MarkdownTableBodyRow(
	override val content: MutableList<MarkdownTableColumn> = mutableListOf()
) : MarkdownTableRow(content) {
	override fun toString(): String {
		require(content.isNotEmpty()) { "Table row column size must be greater than 0." }
		
		return content.joinToString(" | ", "| ", " |")
	}
}

/**Markdown表格的列。*/
data class MarkdownTableColumn(
	override val content: MutableList<MarkdownDslInlineElement> = mutableListOf()
) : MarkdownDslInlineElement, MarkdownDslSuperElement<MarkdownDslInlineElement>, MarkdownDslInlineSuperElement {
	override fun toString(): String {
		//需要将内容中的转义换行符替换成html换行符
		return if(content.isEmpty()) "" else content.joinToString("").replace("\n", "<br>")
	}
}


/**Markdown引文。*/
abstract class MarkdownQuote(
	override val content: MutableList<MarkdownDslElement>
) : MarkdownDslBlockElement, MarkdownDslSuperElement<MarkdownDslElement>, MarkdownDslInlineSuperElement

/**Markdown引文块。*/
data class MarkdownBlockQuote(
	override val content: MutableList<MarkdownDslElement> = mutableListOf()
) : MarkdownQuote(content) {
	override fun toString(): String {
		val contentSnippet = if(content.isEmpty()) "" else content.joinToString("\n")
		return contentSnippet.prependIndent(">" + " ".repeat(quoteIndentSize - 1))
	}
}

/**Markdown缩进块。*/
data class MarkdownIndentedBlock(
	override val content: MutableList<MarkdownDslElement> = mutableListOf()
) : MarkdownQuote(content) {
	override fun toString(): String {
		val contentSnippet = if(content.isEmpty()) "" else content.joinToString("\n")
		return contentSnippet.prependIndent(indent)
	}
}

/**Markdown侧边块。*/
@ExtendedMarkdownFeature
data class MarkdownSideBlock(
	override val content: MutableList<MarkdownDslElement> = mutableListOf()
) : MarkdownQuote(content) {
	override fun toString(): String {
		val contentSnippet = if(content.isEmpty()) "" else content.joinToString("\n")
		return contentSnippet.prependIndent("|" + " ".repeat(indentSize - 1))
	}
}


/**Markdown代码。*/
abstract class MarkdownCode(
	open val code: String
) : MarkdownDslElement

/**Markdown行内代码。*/
data class MarkdownInlineCode(
	override val code: String
) : MarkdownCode(code), MarkdownDslInlineElement {
	override fun toString(): String {
		return "`$code`"
	}
}

/**Markdown代码块。*/
data class MarkdownCodeFence(
	val language: String,
	@ExtendedMarkdownFeature
	val classes: MarkdownAttributeClasses = MarkdownAttributeClasses.empty,
	@ExtendedMarkdownFeature
	val properties: MarkdownAttributeProperties = MarkdownAttributeProperties.empty,
	override val code: String
) : MarkdownCode(code), MarkdownDslBlockElement {
	override fun toString(): String {
		val classesSnippet = classes.toString().ifNotEmpty { " $it" }
		val propertiesSnippet = properties.toString().ifNotEmpty { " $it" }
		return "```$language$classesSnippet$propertiesSnippet\n$code\n```"
	}
}


/**Markdown数学表达式。*/
abstract class MarkdownMath(
	open val code: String
) : MarkdownDslElement

/**Markdown单行数学表达式。*/
data class MarkdownInlineMath(
	override val code: String
) : MarkdownMath(code), MarkdownDslInlineElement {
	override fun toString(): String {
		return "$$code$"
	}
}

/**Markdown多行数学表达式。*/
data class MarkdownMultilineMath(
	override val code: String
) : MarkdownMath(code), MarkdownDslInlineElement {
	override fun toString(): String {
		return "$$\n$code\n$$"
	}
}


/**Markdown警告框。*/
@ExtendedMarkdownFeature
data class MarkdownAlertBox(
	val type: MarkdownAlertBoxType,
	val qualifier: String,
	val title: String,
	override val content: MutableList<MarkdownDslElement> = mutableListOf()
) : MarkdownDslBlockElement, MarkdownDslSuperElement<MarkdownDslElement>, MarkdownDslInlineSuperElement {
	override fun toString(): String {
		val contentSnippet = if(content.isEmpty()) "$indent$truncated" else content.joinToString("\n").prependIndent(indent)
		return "${type.prefixMarkers} $qualifier $quote$title$quote\n$contentSnippet"
	}
}

/**Markdown警告框的类型。*/
@ExtendedMarkdownFeature
enum class MarkdownAlertBoxType(
	val prefixMarkers: String
) {
	Normal("!!!"), Collapsed("???"), Opened("???+")
}

/**Markdown警告框的限定符。*/
@ExtendedMarkdownFeature
enum class MarkdownAlertBoxQualifier(
	val style: String,
	val text: String
) {
	Abstract("abstract", "abstract"), Summary("abstract", "summary"), Tldr("abstract", "tldr"),
	Bug("bug", "bug"),
	Danger("danger", "danger"), Error("danger", "error"),
	Example("example", "example"), Snippet("example", "snippet"),
	Fail("fail", "fail"), Failure("fail", "failure"), Missing("fail", "missing"),
	Question("fag", "question"), Help("fag", "help"), Fag("fag", "fag"),
	Info("info", "info"), Todo("info", "todo"),
	Note("note", "note"), SeeAlso("note", "seealso"),
	Quote("quote", "quote"), Cite("quote", "cite"),
	Success("success", "successs"), Check("success", "check"), Done("success", "done"),
	Tip("tip", "tip"), Hint("tip", "hint"), Important("tip", "important"),
	Warning("warning", "warning"), Caution("warning", "caution"), Attention("warning", "attention")
}


/**Front Matter。只能位于文档顶部。用于配置当前的Markdown文档。使用Yaml格式。*/
@ExtendedMarkdownFeature
data class MarkdownFrontMatter(
	val text: String
) : MarkdownDslBlockElement {
	override fun toString(): String {
		return "---\n$text\n---"
	}
}

/**Markdown目录。用于生成当前文档的目录。*/
@ExtendedMarkdownFeature
class MarkdownToc : MarkdownDslLineElement {
	override fun toString(): String {
		return "[TOC]"
	}
}


/**Markdown导入。用于导入相对路径的图片或文本。*/
@ExtendedMarkdownFeature
data class MarkdownImport(
	val path: String,
	val properties: MarkdownAttributeProperties = MarkdownAttributeProperties.empty
) : MarkdownDslLineElement {
	override fun toString(): String {
		val propertiesSnippet = properties.toString().ifNotEmpty { " $it" }
		return "@import $quote$path$quote$propertiesSnippet"
	}
}

/**Markdown宏。用于重复利用任意Markdown片段。*/
@ExtendedMarkdownFeature
data class MarkdownMacros(
	val name: String
) : MarkdownDslLineElement {
	override fun toString(): String {
		return "<<< $name >>>"
	}
}

/**Markdown宏片段。*/
@ExtendedMarkdownFeature
data class MarkdownMacrosSnippet(
	val name: String,
	override val content: MutableList<MarkdownDslElement> = mutableListOf()
) : MarkdownDslBlockElement, MarkdownDslSuperElement<MarkdownDslElement>, MarkdownDslInlineSuperElement {
	override fun toString(): String {
		val contentSnippet = if(content.isEmpty()) "" else content.joinToString("\n")
		return ">>> $name\n$contentSnippet\n<<<"
	}
}


/**Markdown特性。*/
@ExtendedMarkdownFeature
interface MarkdownAttribute : MarkdownDslElement

/**Markdown特性id。*/
inline class MarkdownAttributeId(
	val value: String
) : MarkdownAttribute {
	override fun toString(): String {
		return "{#$value}"
	}
}

/**Markdown特性类。*/
inline class MarkdownAttributeClasses(
	val value: List<String> = listOf()
) : MarkdownAttribute {
	override fun toString(): String {
		return if(value.isEmpty()) "" else "{${value.joinToString(" ") { ".$value" }}}"
	}
}

/**Markdown特性属性。*/
inline class MarkdownAttributeProperties(
	val value: Map<String, Any?> = mapOf()
) : MarkdownAttribute {
	override fun toString(): String {
		return if(value.isEmpty()) "" else "{${value.entries.joinToString { (k, v) -> "$k=${v.toString()}" }}}"
	}
}


/**构建Markdown的领域专用语言。*/
fun Dsl.Companion.markdown(content: MarkdownDsl.() -> MarkdownDslElement) = MarkdownDsl().also { it.content() }

/**配置Markdown的领域专用语言。*/
fun DslConfig.Companion.markdown(config: MarkdownDslConfig.() -> Unit) = MarkdownDslConfig.config()


/**创建Markdown文本。*/
fun MarkdownDslInlineSuperElement.text(text: String, clearContent: Boolean = false) =
	MarkdownText(text).also { if(clearContent) this.content.clear() }.also { this.content += it }

fun MarkdownDslSuperElement<in MarkdownTextBlock>.textBlock(content: MarkdownTextBlock.() -> Unit) =
	MarkdownTextBlock().also { it.content() }.also { this.content += it }


fun MarkdownDslSuperElement<in MarkdownMainHeader>.header(content: MarkdownMainHeader.() -> Unit) =
	MarkdownMainHeader().also { it.content() }.also { this.content += it }

fun MarkdownDslSuperElement<in MarkdownSubHeader>.subHeader(content: MarkdownSubHeader.() -> Unit) =
	MarkdownSubHeader().also { it.content() }.also { this.content += it }

fun MarkdownDslSuperElement<in MarkdownHeader1>.h1(content: MarkdownHeader1.() -> Unit) =
	MarkdownHeader1().also { it.content() }.also { this.content += it }

fun MarkdownDslSuperElement<in MarkdownHeader2>.h2(content: MarkdownHeader2.() -> Unit) =
	MarkdownHeader2().also { it.content() }.also { this.content += it }

fun MarkdownDslSuperElement<in MarkdownHeader3>.h3(content: MarkdownHeader3.() -> Unit) =
	MarkdownHeader3().also { it.content() }.also { this.content += it }

fun MarkdownDslSuperElement<in MarkdownHeader4>.h4(content: MarkdownHeader4.() -> Unit) =
	MarkdownHeader4().also { it.content() }.also { this.content += it }

fun MarkdownDslSuperElement<in MarkdownHeader5>.h5(content: MarkdownHeader5.() -> Unit) =
	MarkdownHeader5().also { it.content() }.also { this.content += it }

fun MarkdownDslSuperElement<in MarkdownHeader6>.h6(content: MarkdownHeader6.() -> Unit) =
	MarkdownHeader6().also { it.content() }.also { this.content += it }


fun MarkdownDslSuperElement<in MarkdownBoldText>.b(text: String) =
	MarkdownBoldText(text).also { this.content += it }

fun MarkdownDslSuperElement<in MarkdownItalicText>.i(text: String) =
	MarkdownItalicText(text).also { this.content += it }

fun MarkdownDslSuperElement<in MarkdownStrikeLineText>.s(text: String) =
	MarkdownStrikeLineText(text).also { this.content += it }

@ExtendedMarkdownFeature
fun MarkdownDslSuperElement<in MarkdownUnderLineText>.u(text: String) =
	MarkdownUnderLineText(text).also { this.content += it }

@ExtendedMarkdownFeature
fun MarkdownDslSuperElement<in MarkdownHighlightText>.em(text: String) =
	MarkdownHighlightText(text).also { this.content += it }

@ExtendedMarkdownFeature
fun MarkdownDslSuperElement<in MarkdownSuperscriptText>.sup(text: String) =
	MarkdownSuperscriptText(text).also { this.content += it }

@ExtendedMarkdownFeature
fun MarkdownDslSuperElement<in MarkdownSubscriptText>.sub(text: String) =
	MarkdownSubscriptText(text).also { this.content += it }


@ExtendedMarkdownFeature
fun MarkdownDslSuperElement<in CriticMarkupAppendText>.cmAppend(text: String) =
	CriticMarkupAppendText(text).also { this.content += it }

@ExtendedMarkdownFeature
fun MarkdownDslSuperElement<in CriticMarkupDeleteText>.cmDelete(text: String) =
	CriticMarkupDeleteText(text).also { this.content += it }

@ExtendedMarkdownFeature
fun MarkdownDslSuperElement<in CriticMarkupReplaceText>.cmReplace(text: String, replacedText: String) =
	CriticMarkupReplaceText(text, replacedText).also { this.content += it }

@ExtendedMarkdownFeature
fun MarkdownDslSuperElement<in CriticMarkupCommentText>.cmComment(text: String) =
	CriticMarkupCommentText(text).also { this.content += it }

@ExtendedMarkdownFeature
fun MarkdownDslSuperElement<in CriticMarkupHighlightText>.cmHighlight(text: String) =
	CriticMarkupHighlightText(text).also { this.content += it }


@ExtendedMarkdownFeature
fun MarkdownDslSuperElement<in MarkdownAutoLink>.autoLink(url: String) =
	MarkdownAutoLink(url).also { this.content += it }

fun MarkdownDslSuperElement<in MarkdownInlineLink>.link(text: String, url: String, title: String? = null) =
	MarkdownInlineLink(text, url, title).also { this.content += it }

fun MarkdownDslSuperElement<in MarkdownInlineImageLink>.imgLink(text: String, url: String, title: String? = null) =
	MarkdownInlineImageLink(text, url, title).also { this.content += it }

fun MarkdownDslSuperElement<in MarkdownReferenceLink>.refLink(text: String, reference: String, url: String = truncated, title: String? = null) =
	MarkdownReferenceLink(text, reference, url, title).also { this.content += it }

fun MarkdownDslSuperElement<in MarkdownReferenceImageLink>.refImgLink(text: String, reference: String, url: String = truncated, title: String? = null) =
	MarkdownReferenceImageLink(text, reference, url, title).also { this.content += it }

@ExtendedMarkdownFeature
fun MarkdownDslSuperElement<in MarkdownWikiLink>.wikiLink(text: String, url: String) =
	MarkdownWikiLink(text, url).also { this.content += it }


@ExtendedMarkdownFeature
fun MarkdownDslSuperElement<in MarkdownIcon>.icon(name: String) = MarkdownIcon(name).also { this.content += it }

@ExtendedMarkdownFeature
fun MarkdownDslSuperElement<in MarkdownFootNote>.footNote(reference: String, text: String = truncated) =
	MarkdownFootNote(reference, text).also { this.content += it }

@ExtendedMarkdownFeature
fun MarkdownDslSuperElement<in MarkdownAbbreviation>.abbr(reference: String, text: String = truncated) =
	MarkdownAbbreviation(reference, text).also { this.content += it }


fun MarkdownDslSuperElement<in MarkdownList>.list(content: MarkdownList.() -> Unit) =
	MarkdownList().also { it.content() }.also { this.content += it }

fun MarkdownList.ul(content: MarkdownUnorderedListNode.() -> Unit) =
	MarkdownUnorderedListNode().also { it.content() }.also { this.content += it }

fun MarkdownList.ol(order: String, content: MarkdownOrderedListNode.() -> Unit) =
	MarkdownOrderedListNode(order).also { it.content() }.also { this.content += it }

fun MarkdownList.task(completeStatus: Boolean, content: MarkdownTaskListNode.() -> Unit) =
	MarkdownTaskListNode(completeStatus).also { it.content() }.also { this.content += it }

fun MarkdownListNode.ul(content: MarkdownUnorderedListNode.() -> Unit) = this.list.ul(content)

fun MarkdownListNode.ol(order: String, content: MarkdownOrderedListNode.() -> Unit) = this.list.ol(order, content)

fun MarkdownListNode.task(completeStatus: Boolean, content: MarkdownTaskListNode.() -> Unit) = this.list.task(completeStatus, content)


@ExtendedMarkdownFeature
fun MarkdownDslSuperElement<in MarkdownDefinitionList>.defList(title: String, content: MarkdownDefinitionList.() -> Unit) =
	MarkdownDefinitionList(title).also { it.content() }.also { this.content += it }

@ExtendedMarkdownFeature
fun MarkdownDefinitionList.def(content: MarkdownDefinitionListNode.() -> Unit) =
	MarkdownDefinitionListNode().also { it.content() }.also { this.content += it }


fun MarkdownDslSuperElement<in MarkdownTable>.table(content: MarkdownTable.() -> Unit) =
	MarkdownTable().also { it.content() }.also { this.content += it }

fun MarkdownTable.header(content: MarkdownTableHeaderRow.() -> Unit) =
	MarkdownTableHeaderRow().also { it.content() }.also { this.header = it }

fun MarkdownTable.row(content: MarkdownTableBodyRow.() -> Unit) =
	MarkdownTableBodyRow().also { it.content() }.also { this.content += it }

fun MarkdownTableRow.col(content: MarkdownTableColumn.() -> Unit) =
	MarkdownTableColumn().also { it.content() }.also { this.content += it }


fun MarkdownDslSuperElement<in MarkdownBlockQuote>.blockQueue(content: MarkdownBlockQuote.() -> Unit) =
	MarkdownBlockQuote().also { it.content() }.also { this.content += it }

fun MarkdownDslSuperElement<in MarkdownIndentedBlock>.indentedBlock(content: MarkdownIndentedBlock.() -> Unit) =
	MarkdownIndentedBlock().also { it.content() }.also { this.content += it }

@ExtendedMarkdownFeature
fun MarkdownDslSuperElement<in MarkdownSideBlock>.sideBlock(content: MarkdownSideBlock.() -> Unit) =
	MarkdownSideBlock().also { it.content() }.also { this.content += it }


fun MarkdownDslSuperElement<in MarkdownInlineCode>.code(code: String) =
	MarkdownInlineCode(code).also { this.content += it }

fun MarkdownDslSuperElement<in MarkdownCodeFence>.codeFence(language: String, code: () -> String) =
	MarkdownCodeFence(language, code = code()).also { this.content += it }

@ExtendedMarkdownFeature
fun MarkdownDslSuperElement<in MarkdownCodeFence>.codeFence(language: String, classes: MarkdownAttributeClasses, properties: MarkdownAttributeProperties, code: () -> String) =
	MarkdownCodeFence(language, classes, properties, code()).also { this.content += it }


fun MarkdownDslSuperElement<in MarkdownInlineMath>.math(code: String) =
	MarkdownInlineMath(code).also { this.content += it }

fun MarkdownDslSuperElement<in MarkdownMultilineMath>.multilineMath(code: () -> String) =
	MarkdownMultilineMath(code()).also { this.content += it }


@ExtendedMarkdownFeature
fun MarkdownDslSuperElement<in MarkdownAlertBox>.alertBox(type: MarkdownAlertBoxType, qualifier: String, title: String, content: MarkdownAlertBox.() -> Unit) =
	MarkdownAlertBox(type, qualifier, title).also { it.content() }.also { this.content += it }

@ExtendedMarkdownFeature
fun MarkdownDslSuperElement<in MarkdownAlertBox>.alertBox(qualifier: String, title: String, content: MarkdownAlertBox.() -> Unit): MarkdownAlertBox =
	MarkdownAlertBox(MarkdownAlertBoxType.Normal, qualifier, title).also { it.content() }.also { this.content += it }


@ExtendedMarkdownFeature
fun MarkdownDsl.frontMatter(text: () -> String) = MarkdownFrontMatter(text()).also { this.content += it }

@ExtendedMarkdownFeature
fun MarkdownDsl.toc() = MarkdownToc().also { this.content += it }


@ExtendedMarkdownFeature
fun MarkdownDslSuperElement<in MarkdownImport>.import(path: String, properties: MarkdownAttributeProperties) =
	MarkdownImport(path, properties).also { this.content += it }

@ExtendedMarkdownFeature
fun MarkdownDslSuperElement<in MarkdownMacros>.macros(name: String) =
	MarkdownMacros(name).also { this.content += it }

@ExtendedMarkdownFeature
fun MarkdownDslSuperElement<in MarkdownMacrosSnippet>.macrosSnippet(name: String, content: MarkdownMacrosSnippet.() -> Unit) =
	MarkdownMacrosSnippet(name).also { it.content() }.also { this.content += it }


/**配置当前元素的内容间空行数量。默认为1。*/
fun <T : MarkdownDslBlankLineElement> T.bn(blankLineSize: Int) = this.also { it.blankLineSize = blankLineSize }
