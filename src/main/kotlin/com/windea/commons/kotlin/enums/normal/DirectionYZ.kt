package com.windea.commons.kotlin.enums.normal

import com.windea.commons.kotlin.annotation.*
import com.windea.commons.kotlin.enums.normal.Dimension.*

/**基于YZ轴的二维方向。*/
@LocaleText("基于YZ轴的二维方向")
enum class DirectionYZ(
	override val dimension: Array<Dimension>
) : Direction {
	@LocaleText("原点")
	Origin(arrayOf(Point)),
	@LocaleText("上")
	Up(arrayOf(Height)),
	@LocaleText("下")
	Down(arrayOf(Height)),
	@LocaleText("前")
	Forward(arrayOf(Length)),
	@LocaleText("后")
	Backward(arrayOf(Length));
	
	
	override val topDimension = Height
}
