package com.windea.commons.kotlin.enums.fantasy

import com.windea.commons.kotlin.annotation.*
import com.windea.commons.kotlin.enums.fantasy.MagicSuperCategory.*

/**魔法分类。*/
@LocaleText("魔法分类")
enum class MagicCategory(
	val superCategory: MagicSuperCategory
) {
	@LocaleText("风息魔法")
	WindMagic(Charm),
	@LocaleText("冷气魔法")
	ColdMagic(Charm),
	@LocaleText("火炎魔法")
	FireMagic(Charm),
	@LocaleText("电磁魔法")
	ElectricMagic(Charm),
	@LocaleText("次元魔法")
	DimensionMagic(Charm),
	
	@LocaleText("灵魂魔法")
	SoulMagic(Sorcery),
	@LocaleText("结晶魔法")
	CrystalMagic(Sorcery),
	@LocaleText("刻印魔法")
	SealMagic(Sorcery),
	@LocaleText("暗影魔法")
	ShadowMagic(Sorcery),
	@LocaleText("死灵魔法")
	DeadMagic(Sorcery),
	
	@LocaleText("圣光魔法")
	SacredMagic(Aethery),
	@LocaleText("灵光魔法")
	ShineMagic(Aethery),
	@LocaleText("混乱魔法")
	ChaosMagic(Aethery),
	@LocaleText("幽光魔法")
	PsionicMagic(Aethery),
	@LocaleText("星光魔法")
	StarlightMagic(Aethery),
	
	@LocaleText("洞察魔法")
	InsightMagic(Link),
	@LocaleText("召唤魔法")
	SummonMagic(Link),
	@LocaleText("源血魔法")
	SorceriumMagic(Link),
	@LocaleText("虹光魔法")
	AetheriumMagic(Link)
}
