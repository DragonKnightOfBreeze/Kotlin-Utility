package com.windea.commons.kotlin.enums.math

import com.windea.commons.kotlin.annotation.message.*
import com.windea.commons.kotlin.enums.math.Dimension.*

/**一维方向。*/
@Name("一维方向")
enum class Direction1D(
	val dimensions: Array<Dimension>
) {
	@Name("原点")
	Origin(arrayOf(Point)),
	@Name("前")
	Forward(arrayOf(Point, Length)),
	@Name("后")
	Backward(arrayOf(Point, Length))
}
