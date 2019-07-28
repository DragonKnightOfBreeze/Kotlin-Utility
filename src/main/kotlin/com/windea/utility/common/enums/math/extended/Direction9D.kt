package com.windea.utility.common.enums.math.extended

import com.windea.commons.kotlin.annotations.messages.*
import com.windea.commons.kotlin.enums.math.extended.Dimension.*

/**九维方向。第九维指代可变世界线。*/
@Name("九维方向。")
enum class Direction9D(
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
	ParallelPast(arrayOf(Time, MutableTimeLine)),
	@Name("不该存在的未来")
	ShouldNotExistFuture(arrayOf(Time, VariableTimeLine)),
	@Name("不复存在的未来")
	NeverExistFuture(arrayOf(Time, VariableTimeLine)),
	@Name("不该存在的过去")
	ShouldNotExistPast(arrayOf(Time, VariableTimeLine)),
	@Name("不复存在的过去")
	NeverExistPast(arrayOf(Time, VariableTimeLine)),
	
	@Name("现世")
	ThisWorldly(arrayOf(Point, Time)),
	@Name("彼岸")
	OtherWorldly(arrayOf(Point, Time)),
	@Name("别岸")
	AnotherWorldly(arrayOf(Point, Time)),
	@Name("预见的愿景")
	Forevision(arrayOf(Point, Time, WorldLine)),
	@Name("回见的光景")
	Backvision(arrayOf(Point, Time, WorldLine)),
	
	@Name("平行世界")
	ParallelWorld(arrayOf(Point, Time, MutableWorldLine)),
	
	@Name("异度空间")
	StrangeSpace(arrayOf(Point))
}
