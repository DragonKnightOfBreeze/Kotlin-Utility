package com.windea.utility.common.enums.math

import com.windea.utility.common.annotations.messages.*
import com.windea.utility.common.enums.math.Dimension.*

/**基于XY轴的二维方向。*/
@Name("基于XY轴的二维方向")
enum class DirectionXY(
	val dimensions: Array<Dimension>
) {
	@Name("原点")
	Origin(arrayOf(Point)),
	@Name("上")
	Up(arrayOf(Height)),
	@Name("下")
	Down(arrayOf(Height)),
	@Name("左")
	Left(arrayOf(Length)),
	@Name("右")
	Right(arrayOf(Length))
}