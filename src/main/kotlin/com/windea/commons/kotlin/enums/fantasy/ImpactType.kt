package com.windea.commons.kotlin.enums.fantasy

import com.windea.commons.kotlin.annotation.*

/**作用类型。*/
@LocaleText("作用类型")
enum class ImpactType {
	@LocaleText("普通")
	General,
	@LocaleText("斩击")
	Slash,
	@LocaleText("突刺")
	Stub,
	@LocaleText("打击")
	Strike,
	@LocaleText("射击")
	Shoot
}
