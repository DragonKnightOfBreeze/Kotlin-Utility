package com.windea.commons.kotlin.enums.normal

import com.windea.commons.kotlin.annotation.*
import com.windea.commons.kotlin.enums.normal.Dimension.*

/**十维方向。第九维指代变量世界线。*/
@LocaleText("十维方向。")
enum class Direction10D(
	override val dimension: Array<Dimension>
) : Direction {
	@LocaleText("原点")
	Origin(arrayOf(Point)),
	
	@LocaleText("此地")
	ThisPlace(arrayOf(Point)),
	@LocaleText("上")
	Up(arrayOf(Point, Height)),
	@LocaleText("下")
	Down(arrayOf(Point, Height)),
	@LocaleText("前")
	Forward(arrayOf(Point, Length)),
	@LocaleText("后")
	Backward(arrayOf(Point, Length)),
	@LocaleText("左")
	Left(arrayOf(Point, Width)),
	@LocaleText("右")
	Right(arrayOf(Point, Width)),
	
	@LocaleText("现在")
	Now(arrayOf(Time)),
	@LocaleText("未来")
	Future(arrayOf(Time)),
	@LocaleText("过去")
	Past(arrayOf(Time)),
	@LocaleText("预见的未来")
	Foresight(arrayOf(Time, TimeLine)),
	@LocaleText("回见的过去")
	Backsight(arrayOf(Time, TimeLine)),
	
	@LocaleText("平行的现在")
	ParallelNow(arrayOf(Time, MutableTimeLine)),
	@LocaleText("平行的未来")
	ParallelFuture(arrayOf(Time, MutableTimeLine)),
	@LocaleText("平行的过去")
	ParallelPast(arrayOf(Time, MutableTimeLine)),
	@LocaleText("不该存在的未来")
	ShouldNotExistFuture(arrayOf(Time, VariableTimeLine)),
	@LocaleText("不复存在的未来")
	NeverExistFuture(arrayOf(Time, VariableTimeLine)),
	@LocaleText("不该存在的过去")
	ShouldNotExistPast(arrayOf(Time, VariableTimeLine)),
	@LocaleText("不复存在的过去")
	NeverExistPast(arrayOf(Time, VariableTimeLine)),
	
	@LocaleText("现世")
	ThisWorldly(arrayOf(Point, Time)),
	@LocaleText("彼岸")
	OtherWorldly(arrayOf(Point, Time)),
	@LocaleText("别岸")
	AnotherWorldly(arrayOf(Point, Time)),
	@LocaleText("预见的愿景")
	Forevision(arrayOf(Point, Time, WorldLine)),
	@LocaleText("回见的光景")
	Backvision(arrayOf(Point, Time, WorldLine)),
	
	@LocaleText("平行世界")
	ParallelWorld(arrayOf(Point, Time, MutableWorldLine)),
	@LocaleText("不该存在的世界")
	ShouldNotExistWorld(arrayOf(Time, VariableTimeLine)),
	@LocaleText("不复存在的世界")
	NeverExistWorld(arrayOf(Time, VariableTimeLine)),
	
	@LocaleText("异度空间")
	StrangeSpace(arrayOf(Point)),
	@LocaleText("异度时间")
	StrangeTime(arrayOf(Time));
	
	
	override val topDimension = VariableWorldLine
}
