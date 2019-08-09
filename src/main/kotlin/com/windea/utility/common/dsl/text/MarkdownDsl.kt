package com.windea.utility.common.dsl.text

import com.windea.utility.common.annotations.marks.*
import com.windea.utility.common.dsl.*
import com.windea.utility.common.dsl.data.*
import com.windea.utility.common.dsl.text.MarkdownDslConfig.addTrailingHeaderMarkers
import com.windea.utility.common.dsl.text.MarkdownDslConfig.indent
import com.windea.utility.common.dsl.text.MarkdownDslConfig.indentSize
import com.windea.utility.common.dsl.text.MarkdownDslConfig.initialMarker
import com.windea.utility.common.dsl.text.MarkdownDslConfig.performImport
import com.windea.utility.common.dsl.text.MarkdownDslConfig.quote
import com.windea.utility.common.dsl.text.MarkdownDslConfig.quoteIndentSize
import com.windea.utility.common.dsl.text.MarkdownDslConfig.repeatableMarkerSize
import com.windea.utility.common.dsl.text.MarkdownDslConfig.truncated
import com.windea.utility.common.extensions.*
import java.lang.annotation.*

//TODO 关联Html标签

@NotTested
/**Markdown的领域专用语言。*/
data class MarkdownDsl(
	override val content: MutableList<MarkdownDslElement> = mutableListOf()
) : Dsl, MarkdownDslSuperElement<MarkdownDslElement> {
	override fun toString(): String {
		return content.joinToString("\n", "", "\n") { it.toString() }
	}
}

/**Markdown领域专用语言的配置。*/
object MarkdownDslConfig : DslConfig {
	var indentSize: Int = 4
		set(value) {
			field = value.coerceIn(2, 8)
		}
	var quoteIndentSize: Int = 4
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
	var addTrailingHeaderMarkers: Boolean = false
	var addTrailingBreakSpaces: Boolean = true
	
	@ExtendedMarkdownFeature
	var generateToc: Boolean = false
	@ExtendedMarkdownFeature
	var performImport: Boolean = false
	
	internal val indent get() = " ".repeat(indentSize)
	internal val quoteIndent get() = " ".repeat(quoteIndentSize)
	internal val codeIndent get() = " ".repeat(codeIndentSize)
	internal val quote get() = if(preferDoubleQuote) "\"" else "'"
	internal val initialMarker get() = if(preferAsteriskInitialMaker) "*" else "-"
	internal val breakSpaces get() = if(addTrailingHeaderMarkers) "  " else ""
}


/**Markdown领域专用语言的元素。*/
interface MarkdownDslElement

/**Markdown领域专用语言的单行元素。*/
interface MarkdownDslLineElement : MarkdownDslElement

/**Markdown领域专用语言的行内元素。*/
interface MarkdownDslInlineElement : MarkdownDslElement

/**Markdown领域专用语言的引用元素。*/
interface MarkdownDslReferenceElement : MarkdownDslElement, MarkdownDslInlineElement {
	fun toPairString(): Pair<String, String>
}

/**Markdown领域专用语言的块元素。*/
interface MarkdownDslBlockElement : MarkdownDslElement

/**Markdown领域专用语言的父级元素。*/
interface MarkdownDslSuperElement<in T : MarkdownDslElement> : MarkdownDslElement {
	val content: MutableList<in T>
	
	operator fun MarkdownDslElement.plus(element: MarkdownDslElement) = element
}

/**Markdown领域专用语言的行内父级元素。*/
interface MarkdownDslInlineSuperElement : MarkdownDslSuperElement<MarkdownDslInlineElement> {
	operator fun String.unaryPlus() = this@MarkdownDslInlineSuperElement.text(this)
	
	operator fun String.unaryMinus() = this@MarkdownDslInlineSuperElement.text(this, true)
	
	operator fun XmlDslElement.plus(text: String) = (+text)
}

/**Markdown领域专用语言的全局父级元素。*/
interface MarkdownDslAllSuperElement : MarkdownDslSuperElement<MarkdownDslElement> {
	operator fun String.unaryPlus() = this@MarkdownDslAllSuperElement.text(this)
	
	operator fun String.unaryMinus() = this@MarkdownDslAllSuperElement.text(this, true)
	
	operator fun XmlDslElement.plus(text: String) = (+text)
}


/**扩展的Markdown功能。*/
@MustBeDocumented
@Inherited
annotation class ExtendedMarkdownFeature


/**Markdown文本。*/
data class MarkdownText(
	val text: String
) : MarkdownDslInlineElement {
	override fun toString(): String {
		return text
	}
}


/**Markdown标题。*/
abstract class MarkdownHeader(
	open val text: String,
	open val headerLevel: Int
) : MarkdownDslLineElement

/**Setext风格的Markdown标题。*/
abstract class MarkdownSetextHeader(
	override val text: String,
	override val headerLevel: Int
) : MarkdownHeader(text, headerLevel) {
	override fun toString(): String {
		val suffixMarker = if(headerLevel == 1) "=" else "-"
		val suffixMarkers = suffixMarker.repeat(repeatableMarkerSize)
		return "$text\n$suffixMarkers"
	}
}

/**Atx风格的Markdown标题。*/
abstract class MarkdownAtxHeader(
	override val text: String,
	override val headerLevel: Int
) : MarkdownHeader(text, headerLevel) {
	override fun toString(): String {
		val prefixMarkers = "#".repeat(headerLevel) + " "
		val suffixMarkers = if(addTrailingHeaderMarkers) " " + "#".repeat(headerLevel) else ""
		return "$prefixMarkers$text$suffixMarkers"
	}
}

/**Markdown主标题。*/
data class MarkdownMainHeader(
	override val text: String
) : MarkdownSetextHeader(text, 1)

/**Markdown副标题。*/
data class MarkdownSubHeader(
	override val text: String
) : MarkdownSetextHeader(text, 2)

/**Markdown一级标题。*/
data class MarkdownHeader1(
	override val text: String
) : MarkdownAtxHeader(text, 1)

/**Markdown二级标题。*/
data class MarkdownHeader2(
	override val text: String
) : MarkdownAtxHeader(text, 2)

/**Markdown三级标题。*/
data class MarkdownHeader3(
	override val text: String
) : MarkdownAtxHeader(text, 3)

/**Markdown四级标题。*/
data class MarkdownHeader4(
	override val text: String
) : MarkdownAtxHeader(text, 4)

/**Markdown五级标题。*/
data class MarkdownHeader5(
	override val text: String
) : MarkdownAtxHeader(text, 5)

/**Markdown六级标题。*/
data class MarkdownHeader6(
	override val text: String
) : MarkdownAtxHeader(text, 6)

/**Markdown水平分割线。*/
class MarkdownHorizontalLine : MarkdownDslLineElement {
	override fun toString(): String {
		return initialMarker * repeatableMarkerSize
	}
}


/**Markdown富文本。*/
abstract class MarkdownRichText(
	open val text: String,
	protected val prefixMarkers: String,
	protected val suffixMarkers: String
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
data class MarkdownSubscriptText(
	override val text: String
) : MarkdownRichText(text, "~", "~")


/**CriticMarkup文本。*/
@ExtendedMarkdownFeature
abstract class CriticMarkupText(
	open val text: String,
	protected val prefixMarkers: String,
	protected val suffixMarkers: String
) : MarkdownDslInlineElement {
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
	val reference: String,
	override val url: String,
	val title: String? = null
) : MarkdownLink(url), MarkdownDslReferenceElement {
	override fun toPairString(): Pair<String, String> {
		val textSnippet = text?.let { "[$text]" } ?: ""
		val titleSnippet = title?.let { " $quote$title$quote" } ?: ""
		return "$textSnippet[$reference]" to "[$reference]: $url$titleSnippet"
	}
	
	override fun toString(): String {
		return toPairString().first
	}
}

/**Markdown引用图片链接。*/
data class MarkdownReferenceImageLink(
	val text: String? = null,
	val reference: String,
	override val url: String,
	val title: String? = null
) : MarkdownLink(url), MarkdownDslReferenceElement {
	override fun toPairString(): Pair<String, String> {
		val textSnippet = text?.let { "[$text]" } ?: ""
		val titleSnippet = title?.let { " $quote$title$quote" } ?: ""
		return "!$textSnippet[$reference]" to "[$reference]: $url$titleSnippet"
	}
	
	override fun toString(): String {
		return toPairString().first
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
	val reference: String,
	val text: String
) : MarkdownDslReferenceElement {
	override fun toPairString(): Pair<String, String> {
		return "[^$reference]" to "[^$reference]: $text"
	}
	
	override fun toString(): String {
		return toPairString().first
	}
}

/**Markdown缩写。*/
@ExtendedMarkdownFeature
data class MarkdownAbbreviation(
	val abbr: String,
	val text: String
) : MarkdownDslReferenceElement {
	override fun toPairString(): Pair<String, String> {
		return " $abbr " to "*[$abbr]: $text"
	}
	
	override fun toString(): String {
		return toPairString().first
	}
}


/**Markdown特性。*/
@ExtendedMarkdownFeature
abstract class MarkdownAttribute

/**Markdown特性id。*/
data class MarkdownAttributeId(
	val value: String
) : MarkdownAttribute() {
	override fun toString(): String {
		return "{#$value}"
	}
}

/**Markdown特性类。*/
data class MarkdownAttributeClasses(
	val value: List<String>
) : MarkdownAttribute() {
	override fun toString(): String {
		return if(value.isEmpty()) "" else "{${value.joinToString(" ") { ".$value" }}}"
	}
	
	companion object {
		val empty = MarkdownAttributeClasses(emptyList())
	}
}

/**Markdown特性属性。*/
data class MarkdownAttributeProperties(
	val value: Map<String, Any?>
) : MarkdownAttribute() {
	override fun toString(): String {
		return if(value.isEmpty()) "" else "{${value.entries.joinToString { (k, v) -> "$k=${v.toString()}" }}}"
	}
	
	companion object {
		val empty = MarkdownAttributeProperties(emptyMap())
	}
}


//TODO
/**Markdown列表。*/
data class MarkdownList(
	override val content: MutableList<MarkdownListNode> = mutableListOf()
) : MarkdownDslBlockElement, MarkdownDslSuperElement<MarkdownListNode>

/**Markdown列表节点。*/
abstract class MarkdownListNode(
	override val content: MutableList<MarkdownDslInlineElement>
) : MarkdownDslBlockElement, MarkdownDslInlineSuperElement

/**Markdown有序列表节点。*/
data class MarkdownOrderedListNode(
	override val content: MutableList<MarkdownDslInlineElement> = mutableListOf()
) : MarkdownListNode(content)

/**Markdown无序列表节点。*/
data class MarkdownUnorderedListNode(
	override val content: MutableList<MarkdownDslInlineElement> = mutableListOf()
) : MarkdownListNode(content)

/**Markdown任务列表节点。*/
data class MarkdownTaskListNode(
	override val content: MutableList<MarkdownDslInlineElement> = mutableListOf()
) : MarkdownListNode(content)


/**Markdown定义列表。*/
@ExtendedMarkdownFeature
data class MarkdownDefinitionList(
	val title: String,
	override val content: MutableList<MarkdownDefinitionListNode> = mutableListOf(),
	val blankLineSize: Int = 0
) : MarkdownDslBlockElement, MarkdownDslSuperElement<MarkdownDefinitionListNode> {
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
	override val content: MutableList<MarkdownDslElement> = mutableListOf()
) : MarkdownDslBlockElement, MarkdownDslAllSuperElement {
	override fun toString(): String {
		return when {
			content.isEmpty() -> ":" + " ".repeat(indentSize - 1) + truncated
			else -> ":" + content.joinToString("\n").prependIndent(indent).drop(1)
		}
	}
}


//TODO
/**Markdown表格。*/
abstract class MarkdownTable : MarkdownDslBlockElement, MarkdownDslSuperElement<MarkdownTableRow>

/**Markdown表格头部。*/
data class MarkdownTableHeader(
	override val content: MutableList<MarkdownTableColumn> = mutableListOf()
) : MarkdownDslLineElement, MarkdownDslSuperElement<MarkdownTableColumn>

/**Markdown表格行。*/
data class MarkdownTableRow(
	override val content: MutableList<MarkdownTableColumn> = mutableListOf()
) : MarkdownDslLineElement, MarkdownDslSuperElement<MarkdownTableColumn>

/**Markdown表格列。*/
data class MarkdownTableColumn(
	override val content: MutableList<MarkdownDslInlineElement> = mutableListOf()
) : MarkdownDslInlineElement, MarkdownDslInlineSuperElement


//TODO
/**Markdown目录。*/
@ExtendedMarkdownFeature
abstract class MarkdownToc : MarkdownDslBlockElement


/**Markdown引文。*/
abstract class MarkdownQuote(
	override val content: MutableList<MarkdownDslElement>
) : MarkdownDslBlockElement, MarkdownDslAllSuperElement

/**Markdown引文块。*/
data class MarkdownBlockQuote(
	override val content: MutableList<MarkdownDslElement> = mutableListOf()
) : MarkdownQuote(content) {
	override fun toString(): String {
		val contentSnippet = if(content.isEmpty()) "" else content.joinToString("\n") { it.toString() }
		return contentSnippet.prependIndent(">" + " ".repeat(quoteIndentSize - 1))
	}
}

/**Markdown缩进块。*/
data class MarkdownIndentedBlock(
	override val content: MutableList<MarkdownDslElement> = mutableListOf()
) : MarkdownQuote(content) {
	override fun toString(): String {
		val contentSnippet = if(content.isEmpty()) "" else content.joinToString("\n") { it.toString() }
		return contentSnippet.prependIndent(indent)
	}
}

/**Markdown侧边块。*/
@ExtendedMarkdownFeature
data class MarkdownSideBlock(
	override val content: MutableList<MarkdownDslElement> = mutableListOf()
) : MarkdownQuote(content) {
	override fun toString(): String {
		val contentSnippet = if(content.isEmpty()) "" else content.joinToString("\n") { it.toString() }
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
	override val code: String,
	val languageName: String,
	@ExtendedMarkdownFeature
	val id: MarkdownAttributeId? = null,
	@ExtendedMarkdownFeature
	val classes: MarkdownAttributeClasses = MarkdownAttributeClasses.empty,
	@ExtendedMarkdownFeature
	val properties: MarkdownAttributeProperties = MarkdownAttributeProperties.empty
) : MarkdownCode(code), MarkdownDslBlockElement {
	override fun toString(): String {
		val tempAttributesSnippet = arrayOf(id.toString(), classes.toString(), properties.toString()).joinToString(" ")
		val attributesSnippet = if(tempAttributesSnippet.isEmpty()) "" else " $tempAttributesSnippet"
		return "```$languageName$attributesSnippet\n$code\n```"
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
abstract class MarkdownAlertBox(
	val type: MarkdownAlertBoxType,
	open val qualifier: String,
	open val title: String,
	override val content: MutableList<MarkdownDslElement>
) : MarkdownDslBlockElement, MarkdownDslAllSuperElement {
	override fun toString(): String {
		val contentSnippet = if(content.isEmpty()) indent else content.joinToString("\n").prependIndent(indent)
		return "${type.prefixMarkers} $qualifier $quote$title$quote\n$contentSnippet"
	}
}

/**普通的Markdown警告框。*/
data class MarkdownNormalAlertBox(
	override val qualifier: String,
	override val title: String,
	override val content: MutableList<MarkdownDslElement>
) : MarkdownAlertBox(MarkdownAlertBoxType.Normal, qualifier, title, content)

/**折叠的Markdown警告框。*/
data class MarkdownCollapsedAlertBox(
	override val qualifier: String,
	override val title: String,
	override val content: MutableList<MarkdownDslElement>
) : MarkdownAlertBox(MarkdownAlertBoxType.Collapsed, qualifier, title, content)

/**打开的Markdown警告框。*/
data class MarkdownOpenedAlertBox(
	override val qualifier: String,
	override val title: String,
	override val content: MutableList<MarkdownDslElement>
) : MarkdownAlertBox(MarkdownAlertBoxType.Opened, qualifier, title, content)

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


/**Markdown导入。用于导入相对路径的图片或文本。*/
@ExtendedMarkdownFeature
data class MarkdownImport(
	val path: String,
	val properties: MarkdownAttributeProperties = MarkdownAttributeProperties.empty
) : MarkdownDslLineElement {
	fun import(): String {
		TODO()
	}
	
	override fun toString(): String {
		if(performImport) return import()
		val tempPropertiesSnippet = properties.toString()
		val propertiesSnippet = if(tempPropertiesSnippet.isEmpty()) "" else " $tempPropertiesSnippet"
		return "@import $quote$path$quote$propertiesSnippet"
	}
}

/**Markdown宏。用于重复利用任意Markdown片段。*/
@ExtendedMarkdownFeature
data class MarkdownMacros(
	val name: String,
	override val content: MutableList<MarkdownDslElement> = mutableListOf()
) : MarkdownDslReferenceElement, MarkdownDslAllSuperElement {
	override fun toPairString(): Pair<String, String> {
		val contentSnippet = if(content.isEmpty()) "\n" else content.joinToString("\n", "\n", "\n") { it.toString() }
		return "<<< $name >>>" to ">>> $name$contentSnippet<<<"
	}
	
	override fun toString(): String {
		return toPairString().first
	}
}


/**构建Xml的领域专用语言。*/
fun Dsl.Companion.markdown(content: MarkdownDsl.() -> MarkdownDslElement): MarkdownDsl {
	return MarkdownDsl().also { it.content() }
}

/**配置xml的领域专用语言。*/
fun DslConfig.Companion.markdown(config: MarkdownDslConfig.() -> Unit) {
	MarkdownDslConfig.config()
}


//TODO Dsl构建方法
/**创建Xml文本。*/
fun MarkdownDslSuperElement<MarkdownText>.text(text: String, clearContent: Boolean = false): MarkdownText {
	return MarkdownText(text).also {
		if(clearContent) this.content.clear()
		this.content += it
	}
}
