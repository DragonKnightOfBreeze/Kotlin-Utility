package com.windea.utility.common.enums.math

import com.windea.utility.common.annotations.messages.*
import com.windea.utility.common.enums.math.Dimension.*

/**二维方向。*/
@Name("二维方向")
enum class Direction2D(
	val dimensions: Array<Dimension>
) {
	@Name("原点")
	Origin(arrayOf(Point)),
	@Name("前")
	Forward(arrayOf(Point, Length)),
	@Name("后")
	Backward(arrayOf(Point, Length)),
	@Name("左")
	Left(arrayOf(Point, Width)),
	@Name("右")
	Right(arrayOf(Point, Width))
}