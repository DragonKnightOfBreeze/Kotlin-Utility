package com.windea.utility.common.enums.math.extended

import com.windea.commons.kotlin.annotations.messages.*
import com.windea.commons.kotlin.enums.math.extended.Dimension.*

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
