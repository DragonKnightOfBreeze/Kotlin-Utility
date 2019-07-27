package com.windea.commons.kotlin.enums.math.extended

import com.windea.commons.kotlin.annotation.message.*

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
