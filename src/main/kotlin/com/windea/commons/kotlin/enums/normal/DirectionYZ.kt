package com.windea.commons.kotlin.enums.normal

import com.windea.commons.kotlin.annotation.message.*
import com.windea.commons.kotlin.enums.normal.Dimension.*

/**基于YZ轴的二维方向。*/
@Name("基于YZ轴的二维方向")
enum class DirectionYZ(
	override val dimension: Array<Dimension>
) : Direction {
	@Name("原点")
	Origin(arrayOf(Point)),
	@Name("上")
	Up(arrayOf(Height)),
	@Name("下")
	Down(arrayOf(Height)),
	@Name("前")
	Forward(arrayOf(Length)),
	@Name("后")
	Backward(arrayOf(Length));
}
