package com.windea.commons.kotlin.enums.normal

import com.windea.commons.kotlin.annotation.LocaleText

/**
 * 性别。
 */
@LocaleText("性别")
enum class Gender {
	@LocaleText("男性")
	Male,
	@LocaleText("女性")
	Female
}
