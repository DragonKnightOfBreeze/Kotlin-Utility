package com.windea.commons.kotlin.enums.extended

import com.windea.commons.kotlin.annotation.message.*
import com.windea.commons.kotlin.enums.extended.Dimension.*

/**六维方向。第六维指代可变时间线。*/
@Name("六维方向。")
enum class Direction6D(
	val dimension: Array<Dimension>
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
	Backsight(arrayOf(Time, TimeLine)),
	@Name("平行的现在")
	ParallelNow(arrayOf(Time, MutableTimeLine)),
	@Name("平行的未来")
	ParallelFuture(arrayOf(Time, MutableTimeLine)),
	@Name("平行的过去")
	ParallelPast(arrayOf(Time, MutableTimeLine))
}
