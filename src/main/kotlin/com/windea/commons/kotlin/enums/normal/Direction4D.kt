package com.windea.commons.kotlin.enums.normal

import com.windea.commons.kotlin.annotation.*
import com.windea.commons.kotlin.enums.normal.Dimension.*

/**四维方向。第四维指代时间。*/
@LocaleText("四维方向。")
enum class Direction4D(
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
	Past(arrayOf(Time));
	
	
	override val topDimension = Time
}
