package com.windea.commons.kotlin.enums.normal

import com.windea.commons.kotlin.annotation.*
import com.windea.commons.kotlin.enums.normal.Dimension.*

/**基于XY轴的二维方向。*/
@LocaleText("基于XY轴的二维方向")
enum class DirectionXY(
	override val dimension: Array<Dimension>
) : Direction {
	@LocaleText("原点")
	Origin(arrayOf(Point)),
	@LocaleText("上")
	Up(arrayOf(Height)),
	@LocaleText("下")
	Down(arrayOf(Height)),
	@LocaleText("左")
	Left(arrayOf(Length)),
	@LocaleText("右")
	Right(arrayOf(Length));
	
	
	override val topDimension = Height
}
