package com.windea.commons.kotlin.enums.fantasy

import com.windea.commons.kotlin.annotation.*

/**作用属性。*/
@LocaleText("作用属性")
enum class ImpactAttribute {
	@LocaleText("物理")
	Physics,
	@LocaleText("化学")
	Chemical,
	@LocaleText("灵魂")
	Soul,
	@LocaleText("灵力")
	Spirit,
	@LocaleText("风息")
	Wind,
	@LocaleText("冷气")
	Cold,
	@LocaleText("火炎")
	Fire,
	@LocaleText("电磁")
	Electric
}
