package com.windea.commons.kotlin.enums.normal

import com.windea.commons.kotlin.annotation.message.*
import com.windea.commons.kotlin.enums.normal.Dimension.*

/**基于XY轴的二维方向。*/
@Name("基于XY轴的二维方向")
enum class DirectionXY(
	override val dimension: Array<Dimension>
) : Direction {
	@Name("原点")
	Origin(arrayOf(Point)),
	@Name("上")
	Up(arrayOf(Height)),
	@Name("下")
	Down(arrayOf(Height)),
	@Name("左")
	Left(arrayOf(Length)),
	@Name("右")
	Right(arrayOf(Length));
}
