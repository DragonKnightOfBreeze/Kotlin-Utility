package com.windea.utility.common.enums.math.extended

import com.windea.utility.common.annotations.messages.*
import com.windea.utility.common.enums.math.extended.Dimension.*

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
