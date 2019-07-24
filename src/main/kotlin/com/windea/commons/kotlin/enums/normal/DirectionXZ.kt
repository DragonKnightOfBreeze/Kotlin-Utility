package com.windea.commons.kotlin.enums.normal

import com.windea.commons.kotlin.annotation.*
import com.windea.commons.kotlin.enums.normal.Dimension.*

/**基于XZ轴的二维方向。*/
@LocaleText("基于XZ轴的二维方向")
enum class DirectionXZ(
	override val dimension: Array<Dimension>
) : Direction {
	@LocaleText("原点")
	Origin(arrayOf(Point)),
	@LocaleText("前")
	Forward(arrayOf(Length)),
	@LocaleText("后")
	Backward(arrayOf(Length)),
	@LocaleText("左")
	Left(arrayOf(Width)),
	@LocaleText("右")
	Right(arrayOf(Width));
	
	
	override val topDimension = Width
}
