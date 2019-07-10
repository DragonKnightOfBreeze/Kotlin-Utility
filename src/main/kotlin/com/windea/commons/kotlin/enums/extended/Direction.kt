package com.windea.commons.kotlin.enums.extended

import com.windea.commons.kotlin.annotation.*

/**扩展的方向。*/
@LocaleText("方向")
enum class Direction {
	@LocaleText("东")
	East,
	@LocaleText("南")
	South,
	@LocaleText("西")
	West,
	@LocaleText("北")
	North,
	@LocaleText("东南")
	SouthEast,
	@LocaleText("西南")
	SouthWest,
	@LocaleText("东北")
	NorthEast,
	@LocaleText("西北")
	NorthWest
}
