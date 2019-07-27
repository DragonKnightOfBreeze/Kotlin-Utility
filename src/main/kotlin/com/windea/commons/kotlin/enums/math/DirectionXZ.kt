package com.windea.commons.kotlin.enums.math

import com.windea.commons.kotlin.annotation.message.*
import com.windea.commons.kotlin.enums.math.Dimension.*

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
