package com.windea.commons.kotlin.enums.normal

import com.windea.commons.kotlin.annotation.*

/**顶级维度。*/
@LocaleText("顶级维度")
enum class SuperDimension {
	@LocaleText("虚空")
	Void,
	@LocaleText("空间")
	GenericSpace,
	@LocaleText("时间")
	GenericTime,
	@LocaleText("时间线")
	GenericTimeLine,
	@LocaleText("世界线")
	GenericWorldLine,
}
