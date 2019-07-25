package com.windea.commons.kotlin.enums.normal

import com.windea.commons.kotlin.annotation.message.*
import com.windea.commons.kotlin.enums.normal.Dimension.*

/**二维方向。*/
@Name("二维方向")
enum class Direction2D(
	override val dimension: Array<Dimension>
) : Direction {
	@Name("原点")
	Origin(arrayOf(Point)),
	@Name("前")
	Forward(arrayOf(Point, Length)),
	@Name("后")
	Backward(arrayOf(Point, Length)),
	@Name("左")
	Left(arrayOf(Point, Width)),
	@Name("右")
	Right(arrayOf(Point, Width));
}
