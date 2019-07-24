package com.windea.commons.kotlin.enums.fantasy

import com.windea.commons.kotlin.annotation.*
import com.windea.commons.kotlin.enums.fantasy.WeaponSuperCategory.*

/**武器分类。*/
@LocaleText("武器分类")
enum class WeaponCategory(
	val superCategory: WeaponSuperCategory
) {
	@LocaleText("匕首")
	Dagger(BladeWeapon),
	@LocaleText("刺剑")
	Rapier(BladeWeapon),
	@LocaleText("直剑")
	StraightSword(BladeWeapon),
	@LocaleText("大剑")
	GreatSword(BladeWeapon),
	@LocaleText("特大剑")
	UltraGreatSword(BladeWeapon),
	@LocaleText("曲刀")
	CurvedSword(BladeWeapon),
	@LocaleText("大曲刀")
	GreatCurvedSword(BladeWeapon),
	@LocaleText("刀")
	Katana(BladeWeapon),
	@LocaleText("鞭")
	Whip(BladeWeapon),
	
	@LocaleText("枪")
	Spear(LongArmWeapon),
	@LocaleText("长枪")
	Lance(LongArmWeapon),
	@LocaleText("戟")
	Hambert(LongArmWeapon),
	@LocaleText("镰刀")
	Scythe(LongArmWeapon),
	
	@LocaleText("长棍")
	QuarterStaff(StrikeWeapon),
	@LocaleText("槌")
	Hammer(StrikeWeapon),
	@LocaleText("大槌")
	GreatHammer(StrikeWeapon),
	
	@LocaleText("弓")
	Bow(ShootWeapon),
	@LocaleText("大弓")
	GreatBow(ShootWeapon),
	@LocaleText("弩")
	Crossbow(ShootWeapon),
	@LocaleText("手枪")
	Pistol(ShootWeapon),
	@LocaleText("轻枪械")
	LightRifle(ShootWeapon),
	@LocaleText("重枪械")
	HeavyRifle(ShootWeapon),
	
	@LocaleText("小型盾")
	SmallShield(Shield),
	@LocaleText("中型盾")
	MediumShield(Shield),
	@LocaleText("大型盾")
	GreatShield(Shield),
	
	@LocaleText("法杖")
	Staff(MagicInterface),
	@LocaleText("魔法石")
	MagicStone(MagicInterface),
	@LocaleText("护符")
	Talisman(MagicInterface),
	@LocaleText("圣铃")
	Chime(MagicInterface),
	@LocaleText("书籍")
	Book(MagicInterface)
}
