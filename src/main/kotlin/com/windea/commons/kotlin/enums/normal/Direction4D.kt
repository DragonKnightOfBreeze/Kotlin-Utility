package com.windea.commons.kotlin.enums.normal

import com.windea.commons.kotlin.annotation.*
import com.windea.commons.kotlin.enums.normal.Dimension.*

/**四维方向。第四维指代时间。*/
@Name("四维方向。")
enum class Direction4D(
	override val dimension: Array<Dimension>
) : Direction {
	@Name("原点")
	Origin(arrayOf(Point)),
	
	@Name("此地")
	ThisPlace(arrayOf(Point)),
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
	Right(arrayOf(Point, Width)),
	
	@Name("现在")
	Now(arrayOf(Time)),
	@Name("未来")
	Future(arrayOf(Time)),
	@Name("过去")
	Past(arrayOf(Time));
}
