package com.windea.commons.kotlin.enums.normal

import com.windea.commons.kotlin.annotation.*

/**地理方向。*/
@Name("地理方向")
enum class GeographyDirection {
	@Name("原点")
	Origin,
	@Name("东")
	East,
	@Name("南")
	South,
	@Name("西")
	West,
	@Name("北")
	North
}
