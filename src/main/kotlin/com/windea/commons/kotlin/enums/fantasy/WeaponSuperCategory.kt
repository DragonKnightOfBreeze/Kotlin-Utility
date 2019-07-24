package com.windea.commons.kotlin.enums.fantasy

import com.windea.commons.kotlin.annotation.*

/**武器顶级分类。*/
@LocaleText("武器顶级分类")
enum class WeaponSuperCategory {
	@LocaleText("刀刃武器")
	BladeWeapon,
	@LocaleText("长柄武器")
	LongArmWeapon,
	@LocaleText("斩击武器")
	SlashWeapon,
	@LocaleText("打击武器")
	StrikeWeapon,
	@LocaleText("射击武器")
	ShootWeapon,
	@LocaleText("盾牌")
	Shield,
	@LocaleText("魔法盾牌")
	MagicInterface
}
