package com.windea.commons.kotlin.enums.fantasy

import com.windea.commons.kotlin.annotation.*

/**武器顶级分类。*/
@LocaleText("武器顶级分类。")
enum class MagicSuperCategory {
	@LocaleText("法术")
	Charm,
	@LocaleText("咒术")
	Sorcery,
	@LocaleText("灵术")
	Aethery,
	@LocaleText("精灵术")
	Link
}
