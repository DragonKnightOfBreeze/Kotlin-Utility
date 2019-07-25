package com.windea.commons.kotlin.enums.fantasy

import com.windea.commons.kotlin.annotation.message.*

/**武器顶级分类。*/
@Name("武器顶级分类")
enum class WeaponSuperCategory {
	@Name("刀刃武器")
	BladeWeapon,
	@Name("长柄武器")
	LongArmWeapon,
	@Name("斩击武器")
	SlashWeapon,
	@Name("打击武器")
	StrikeWeapon,
	@Name("射击武器")
	ShootWeapon,
	@Name("盾牌")
	Shield,
	@Name("魔法盾牌")
	MagicInterface
}
