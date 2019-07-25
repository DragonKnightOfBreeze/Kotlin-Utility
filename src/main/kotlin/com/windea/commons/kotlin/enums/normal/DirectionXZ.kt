package com.windea.commons.kotlin.enums.normal

import com.windea.commons.kotlin.annotation.*
import com.windea.commons.kotlin.enums.normal.Dimension.*

/**基于XZ轴的二维方向。*/
@Name("基于XZ轴的二维方向")
enum class DirectionXZ(
	override val dimension: Array<Dimension>
) : Direction {
	@Name("原点")
	Origin(arrayOf(Point)),
	@Name("前")
	Forward(arrayOf(Length)),
	@Name("后")
	Backward(arrayOf(Length)),
	@Name("左")
	Left(arrayOf(Width)),
	@Name("右")
	Right(arrayOf(Width));
}
