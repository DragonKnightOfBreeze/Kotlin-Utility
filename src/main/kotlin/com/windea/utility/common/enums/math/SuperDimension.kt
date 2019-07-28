package com.windea.utility.common.enums.math

import com.windea.commons.kotlin.annotations.messages.*

/**顶级维度。*/
@Name("顶级维度")
enum class SuperDimension {
	@Name("虚空")
	Void,
	@Name("空间")
	GenericSpace,
	@Name("时间")
	GenericTime,
	@Name("时间线")
	GenericTimeLine,
	@Name("世界线")
	GenericWorldLine,
}
