package com.windea.utility.common.enums.math.extended

import com.windea.utility.common.annotations.messages.*
import com.windea.utility.common.enums.math.extended.Dimension.*

/**基于XZ轴的二维方向。*/
@Name("基于XZ轴的二维方向")
enum class DirectionXZ(
	val dimensions: Array<Dimension>
) {
	@Name("原点")
	Origin(arrayOf(Point)),
	@Name("前")
	Forward(arrayOf(Length)),
	@Name("后")
	Backward(arrayOf(Length)),
	@Name("左")
	Left(arrayOf(Width)),
	@Name("右")
	Right(arrayOf(Width))
}
