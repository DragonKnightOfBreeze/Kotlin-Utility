package com.windea.utility.common.enums.math

import com.windea.commons.kotlin.annotations.messages.*
import com.windea.commons.kotlin.enums.math.Dimension.*

/**基于YZ轴的二维方向。*/
@Name("基于YZ轴的二维方向")
enum class DirectionYZ(
	val dimensions: Array<Dimension>
) {
	@Name("原点")
	Origin(arrayOf(Point)),
	@Name("上")
	Up(arrayOf(Height)),
	@Name("下")
	Down(arrayOf(Height)),
	@Name("前")
	Forward(arrayOf(Length)),
	@Name("后")
	Backward(arrayOf(Length))
}
