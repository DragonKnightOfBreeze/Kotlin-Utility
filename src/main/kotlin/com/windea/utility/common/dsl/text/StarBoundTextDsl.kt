package com.windea.utility.common.dsl.text

import com.windea.utility.common.annotations.marks.*
import com.windea.utility.common.dsl.*

/**StarBound富文本的领域专用语言。*/
@NotTested
@NotSuitable("直接从json/yaml/patch文件生成StarBound富文本时")
class StarBoundTextDsl : Dsl {
	val builder: StringBuilder = StringBuilder()
	
	override fun generate(): String {
		return builder.toString()
	}
}


interface StarBoundTextDslElement

data class ColorfulText(
	val color: String,
	val text: String
) : StarBoundTextDslElement {
	override fun toString(): String {
		return "^$color;$text^reset;"
	}
}

data class PlaceHolderText(
	val placeHolder: String
) : StarBoundTextDslElement {
	override fun toString(): String {
		return "<$placeHolder>"
	}
}


enum class StarBoundColor(
	val text: String
) {
	White("white"), Grey("grey"), Black("black"), Red("red"), Yellow("yellow"), Blue("blue"),
	Green("green"), Cyan("cyan"), Purple("purple")
}

enum class StarBoundPlaceHolder(
	val text: String
) {
	PlayerName("player_name")
}


fun Dsl.Companion.starBoundText(text: StarBoundTextDsl.() -> String): StarBoundTextDsl {
	return StarBoundTextDsl().apply { this.builder.append(this.text()) }
}

fun StarBoundTextDsl.ct(color: String, text: StarBoundTextDsl.() -> String): String {
	return ColorfulText(color, this.text()).toString()
}

fun StarBoundTextDsl.ct(color: StarBoundColor, text: StarBoundTextDsl.() -> String) = this.ct(color.text, text)

fun StarBoundTextDsl.pht(placeHolder: String): String {
	return PlaceHolderText(placeHolder).toString()
}

fun StarBoundTextDsl.pht(placeHolder: StarBoundPlaceHolder) = this.pht(placeHolder.text)

