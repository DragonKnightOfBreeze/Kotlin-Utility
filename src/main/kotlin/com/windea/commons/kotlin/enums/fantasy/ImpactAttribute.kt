package com.windea.commons.kotlin.enums.fantasy

import com.windea.commons.kotlin.annotation.message.*

/**作用属性。*/
@Name("作用属性")
enum class ImpactAttribute {
	@Name("物理")
	Physics,
	@Name("化学")
	Chemical,
	@Name("灵魂")
	Soul,
	@Name("灵力")
	Spirit,
	@Name("风息")
	Wind,
	@Name("冷气")
	Cold,
	@Name("火炎")
	Fire,
	@Name("电磁")
	Electric
}
