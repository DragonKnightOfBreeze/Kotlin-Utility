package com.windea.commons.kotlin.enums.normal

import com.windea.commons.kotlin.annotation.*

/**地理方向。*/
@LocaleText("地理方向")
enum class GeographyDirection {
	@LocaleText("原点")
	Origin,
	@LocaleText("东")
	East,
	@LocaleText("南")
	South,
	@LocaleText("西")
	West,
	@LocaleText("北")
	North
}
