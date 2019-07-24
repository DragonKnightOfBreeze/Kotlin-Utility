package com.windea.commons.kotlin.enums.normal

import com.windea.commons.kotlin.annotation.*

/**扩展的地理方向。*/
@LocaleText("扩展的地理方向")
enum class GeographyDirectionEx {
	@LocaleText("原点")
	Origin,
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
