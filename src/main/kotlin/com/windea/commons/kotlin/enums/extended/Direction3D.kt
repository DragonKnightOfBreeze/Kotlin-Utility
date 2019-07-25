package com.windea.commons.kotlin.enums.extended

import com.windea.commons.kotlin.annotation.message.*
import com.windea.commons.kotlin.enums.extended.Dimension.*

/**三维方向。*/
@Name("三维方向")
enum class Direction3D(
	val dimensions: Array<Dimension>
) {
	@Name("原点")
	Origin(arrayOf(Point)),
	@Name("上")
	Up(arrayOf(Point, Height)),
	@Name("下")
	Down(arrayOf(Point, Height)),
	@Name("前")
	Forward(arrayOf(Point, Length)),
	@Name("后")
	Backward(arrayOf(Point, Length)),
	@Name("左")
	Left(arrayOf(Point, Width)),
	@Name("右")
	Right(arrayOf(Point, Width));
}
