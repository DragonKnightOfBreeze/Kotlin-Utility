package com.windea.commons.kotlin.enums.extended

import com.windea.commons.kotlin.annotation.*

/**扩展的性别。*/
@LocaleText("性别")
enum class Gender {
	@LocaleText("男性")
	Male,
	@LocaleText("女性")
	Female,
	@LocaleText("泛性别")
	Generic,
	@LocaleText("无性别")
	None,
	@LocaleText("男性（模拟）")
	SimulateMale,
	@LocaleText("女性（模拟）")
	SimulateFemale,
	@LocaleText("泛性别（模拟）")
	SimulateGeneric
}
