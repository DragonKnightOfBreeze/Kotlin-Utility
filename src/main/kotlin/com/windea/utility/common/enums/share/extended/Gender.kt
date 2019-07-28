package com.windea.utility.common.enums.share.extended

import com.windea.commons.kotlin.annotations.messages.*

/**性别。*/
@Name("性别")
enum class Gender {
	@Name("无性别")
	None,
	@Name("男性")
	Male,
	@Name("女性")
	Female,
	@Name("泛性别")
	Generic,
	@Name("男性（模拟）")
	SimulateMale,
	@Name("女性（模拟）")
	SimulateFemale,
	@Name("泛性别（模拟）")
	SimulateGeneric
}
