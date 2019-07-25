package com.windea.commons.kotlin.enums.normal

import com.windea.commons.kotlin.annotation.*
import com.windea.commons.kotlin.enums.normal.Dimension.*

/**十维方向。第九维指代变量世界线。*/
@Name("十维方向。")
enum class Direction10D(
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
	@Name("不该存在的世界")
	ShouldNotExistWorld(arrayOf(Time, VariableTimeLine)),
	@Name("不复存在的世界")
	NeverExistWorld(arrayOf(Time, VariableTimeLine)),
	
	@Name("异度空间")
	StrangeSpace(arrayOf(Point)),
	@Name("异度时间")
	StrangeTime(arrayOf(Time));
}
