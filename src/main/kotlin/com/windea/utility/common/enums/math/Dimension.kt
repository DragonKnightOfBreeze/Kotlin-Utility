package com.windea.utility.common.enums.math

import com.windea.utility.common.annotations.messages.*
import com.windea.utility.common.enums.math.SuperDimension.*

/**维度。*/
@Name("维度")
enum class Dimension(
	val superDimension: SuperDimension
) {
	@Name("点")
	Point(Void),
	@Name("长度")
	Length(GenericSpace),
	@Name("宽度")
	Width(GenericSpace),
	@Name("高度")
	Height(GenericSpace)
}
