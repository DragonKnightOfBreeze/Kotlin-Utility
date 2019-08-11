package com.windea.utility.common.dsl.text

import com.windea.utility.common.annotations.marks.*
import com.windea.utility.common.dsl.*

/**StarBound富文本的领域专用语言。*/
@NotSuitable("直接从json/yaml/patch文件生成StarBound富文本时")
data class StarBoundTextDsl(
	override val content: MutableList<StarBoundTextDslElement> = mutableListOf()
) : Dsl, StarBoundTextDslSuperElement {
	override fun toString(): String {
		return content.joinToString("")
	}
}


/**StarBound富文本的元素。*/
interface StarBoundTextDslElement

/**StarBound富文本的父级元素。*/
interface StarBoundTextDslSuperElement : StarBoundTextDslElement {
	val content: MutableList<StarBoundTextDslElement>
	
	operator fun StarBoundTextDslElement.plus(element: StarBoundTextDslElement) = element
	
	operator fun String.unaryPlus() = this@StarBoundTextDslSuperElement.t(this)
	
	operator fun String.unaryMinus() = this@StarBoundTextDslSuperElement.t(this, true)
	
	operator fun StarBoundTextDslElement.plus(text: String) = (+text)
	
}


/**StarBound普通文本。*/
inline class StarBoundText(
	val text: String
) : StarBoundTextDslElement {
	override fun toString(): String {
		return text
	}
}

/**StarBound占位文本。*/
data class StarBoundPlaceHolderText(
	val placeHolder: String
) : StarBoundTextDslElement {
	override fun toString(): String {
		return "<$placeHolder>"
	}
}

/**StarBound彩色文本。*/
data class StarBoundColorText(
	val color: String,
	override val content: MutableList<StarBoundTextDslElement> = mutableListOf()
) : StarBoundTextDslSuperElement {
	override fun toString(): String {
		val contentSnippet = content.joinToString("")
		return "^$color;$contentSnippet^reset;"
	}
}

/**StarBound占位符。*/
enum class StarBoundPlaceHolder(
	val text: String
) {
	PlayerName("player_name")
}

/**StarBound颜色。*/
enum class StarBoundColor(
	val text: String
) {
	White("white"), Grey("grey"), Black("black"), Red("red"), Yellow("yellow"), Blue("blue"),
	Green("green"), Cyan("cyan"), Purple("purple")
}


/**构建StarBound富文本的领域专用语言。*/
fun Dsl.Companion.starBoundText(content: StarBoundTextDsl.() -> Unit) = StarBoundTextDsl().also { it.content() }


/**创建StarBound文本。*/
fun StarBoundTextDslSuperElement.t(text: String, clearContent: Boolean = false) =
	StarBoundText(text).also { if(clearContent) this.content.clear() }.also { this.content += it }

/**创建StarBound占位文本。*/
fun StarBoundTextDslSuperElement.pht(placeHolder: String) =
	StarBoundPlaceHolderText(placeHolder).also { this.content += it }

/**创建StarBound占位文本。*/
fun StarBoundTextDslSuperElement.pht(placeHolder: StarBoundPlaceHolder) = this.pht(placeHolder.text)

/**创建StarBound彩色文本。*/
fun StarBoundTextDslSuperElement.ct(color: String, content: StarBoundColorText.() -> Unit) =
	StarBoundColorText(color).also { it.content() }.also { this.content += it }

/**创建StarBound彩色文本。*/
fun StarBoundTextDslSuperElement.ct(color: StarBoundColor, content: StarBoundColorText.() -> Unit) = this.ct(color.text, content)

