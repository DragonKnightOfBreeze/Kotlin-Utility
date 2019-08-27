package com.windea.utility.springboot.components.converters

import org.springframework.core.convert.converter.*

/**字符串到整数范围的转化器。*/
class StringToIntRangeConverter : Converter<String, IntRange> {
	override fun convert(source: String): IntRange? {
		return source.split("..").map { it.trim().toInt() }.let { it[0]..it[1] }
	}
}


/**字符串到长整数范围的转化器。*/
class StringToLongRangeConverter : Converter<String, LongRange> {
	override fun convert(source: String): LongRange? {
		return source.split("..").map { it.trim().trimEnd('l', 'L').toLong() }.let { it[0]..it[1] }
	}
}


/**字符串到单精度浮点数范围的转化器。*/
class StringToFloatRangeConverter : Converter<String, ClosedFloatingPointRange<Float>> {
	override fun convert(source: String): ClosedFloatingPointRange<Float>? {
		return source.split("..").map { it.trim().trimEnd('f', 'F').toFloat() }.let { it[0]..it[1] }
	}
}


/**字符串到双精度浮点数范围的转化器。*/
class StringToDoubleRangeConverter : Converter<String, ClosedFloatingPointRange<Double>> {
	override fun convert(source: String): ClosedFloatingPointRange<Double>? {
		return source.split("..").map { it.trim().toDouble() }.let { it[0]..it[1] }
	}
}
