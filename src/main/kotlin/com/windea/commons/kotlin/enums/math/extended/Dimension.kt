package com.windea.commons.kotlin.enums.math.extended

import com.windea.commons.kotlin.annotation.message.*
import com.windea.commons.kotlin.enums.math.extended.SuperDimension.*

/**维度。*/
@Name("维度")
enum class Dimension(
	val superDimension: SuperDimension
) {
	@Name("点")
	Point(Void),
	@Name("长度")
	Length(GenericSpace),
	@Name("宽度")
	Width(GenericSpace),
	@Name("高度")
	Height(GenericSpace),
	@Name("时间")
	Time(GenericTime),
	@Name("时间线")
	@Description("""
将时间看作一条线，现在可以折叠时间线，借此你可以穿梭到过去或未来。
然而，这些过去或未来都是命中注定，无法改变的。你的命运，无从选择。
	""")
	TimeLine(GenericTimeLine),
	@Name("可变时间线")
	@Description("""
假定存在数条时间线，现在可以从一条时间线跳跃到另一条时间线，借此你可以穿梭到不同的过去或未来。
然而，这些时间线仍然是固定的，无法改变的。你的命运，虽然有了选择的余地，却也只是x%或0%的可能性。
	""")
	MutableTimeLine(GenericTimeLine),
	@Name("变量时间线")
	@Description("""
假定存在数条可变时间线，现在可以创建或删除时间线，借此你可以自由选择和进入自己的过去和未来。
然而，这些时间线仍然只是**仅属于你**的时间线。你能掌控自己的命运，却依旧无法直接掌控别人的命运。
	""")
	VariableTimeLine(GenericTimeLine),
	@Name("世界线")
	@Description("""
将时间线中的时间概念扩展到世界尺度，此后你可以知晓他人的过去和未来，并能引导那人。
到此为止，你已拥有通晓古今的能力。你被世人称为`先知`，但这一称呼，亦暗示了你对改变别人命运的无能为力。
	""")
	WorldLine(GenericWorldLine),
	@Name("可变世界线")
	@Description("""
将可变时间线中的时间概念扩展到世界尺度，此后你可以知晓他人的过去和未来的所有可能性，并能引导那人。
到此为止，你已拥有选择他人命运的能力。你被世人称为`引路人`，但这一称呼，并未包含明确的褒义。
	""")
	MutableWorldLine(GenericWorldLine),
	@Name("变量世界线")
	@Description("""
将变量时间线中的时间概念扩展到世界尺度，此后你可以创造和抹除他人的过去和未来的所有可能性。
到此为止，你已拥有改变他人命运的能力。你被世人称为`奇迹师`，但这一称呼，仅仅属于天性善良且能永保善良的人。
但是最后，你仍然无法读取他人的内心，仍然无法保持自身的不朽。到达此路的终点，你会发现——你仍然有所不能。
	""")
	VariableWorldLine(GenericWorldLine);
}

