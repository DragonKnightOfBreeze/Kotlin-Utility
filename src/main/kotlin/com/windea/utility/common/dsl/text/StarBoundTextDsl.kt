package com.windea.utility.common.dsl.text

import com.windea.utility.common.annotations.marks.*
import com.windea.utility.common.dsl.*

/**StarBound富文本的领域专用语言。*/
@NotTested
@NotSuitable("直接从json/yaml/patch文件生成StarBound富文本时")
class StarBoundTextDsl : Dsl {
	val content: MutableList<StarBoundTextDslElement> = mutableListOf()
	
	override fun toString(): String {
		return content.joinToString("") { it.toString() }
	}
}

/**StarBound富文本的元素。*/
interface StarBoundTextDslElement

/**StarBound普通文本。*/
data class StarBoundText(
	val text: String
) : StarBoundTextDslElement {
	override fun toString(): String {
		return text
	}
}

/**StarBound彩色文本。*/
data class StarBoundColorText(
	val color: String,
	val text: String
) : StarBoundTextDslElement {
	override fun toString(): String {
		return "^$color;$text^reset;"
	}
}

/**StarBound占位文本。*/
data class PlaceHolderText(
	val placeHolder: String
) : StarBoundTextDslElement {
	override fun toString(): String {
		return "<$placeHolder>"
	}
}


/**StarBound颜色。*/
enum class StarBoundColor(
	val text: String
) {
	White("white"), Grey("grey"), Black("black"), Red("red"), Yellow("yellow"), Blue("blue"),
	Green("green"), Cyan("cyan"), Purple("purple")
}

/**StarBound占位符。*/
enum class StarBoundPlaceHolder(
	val text: String
) {
	PlayerName("player_name")
}


/**构建StarBound富文本的领域专用语言。*/
fun Dsl.Companion.starBoundText(content: StarBoundTextDsl.() -> Unit): StarBoundTextDsl {
	return StarBoundTextDsl().also { it.content() }
}

/**构建StarBound富文本的字符串。*/
fun Dsl.Companion.starBoundTextString(string: StarBoundTextDsl.() -> String): String {
	return StarBoundTextDsl().string()
}


fun StarBoundTextDsl.t(text: () -> String): StarBoundText {
	return StarBoundText(text()).also { this.content += it }
}

/**创建StarBound彩色文本。*/
fun StarBoundTextDsl.ct(color: String, text: () -> String): StarBoundColorText {
	return StarBoundColorText(color, text()).also { this.content += it }
}

/**创建StarBound彩色文本。*/
fun StarBoundTextDsl.ct(color: StarBoundColor, text: () -> String) = this.ct(color.text, text)

/**创建StarBound占位文本。*/
fun StarBoundTextDsl.pht(placeHolder: String): PlaceHolderText {
	return PlaceHolderText(placeHolder).also { this.content += it }
}

/**创建StarBound占位文本。*/
fun StarBoundTextDsl.pht(placeHolder: StarBoundPlaceHolder) = this.pht(placeHolder.text)

