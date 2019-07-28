package com.windea.utility.common.enums.math.extended

import com.windea.commons.kotlin.annotations.messages.*
import com.windea.commons.kotlin.enums.math.extended.Dimension.*

/**五维方向。第五维指代时间线。*/
@Name("五维方向。")
enum class Direction5D(
	val dimensions: Array<Dimension>
) {
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
	Past(arrayOf(Time)),
	@Name("预见的未来")
	Foresight(arrayOf(Time, TimeLine)),
	@Name("回见的过去")
	Backsight(arrayOf(Time, TimeLine))
}
