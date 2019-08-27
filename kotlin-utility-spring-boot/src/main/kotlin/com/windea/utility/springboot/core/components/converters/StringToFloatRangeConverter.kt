package com.windea.utility.springboot.core.components.converters

import org.springframework.core.convert.converter.*
import org.springframework.stereotype.*

/**字符串到单精度浮点数范围的转化器。*/
@Component
class StringToFloatRangeConverter : Converter<String, ClosedFloatingPointRange<Float>> {
	override fun convert(source: String): ClosedFloatingPointRange<Float>? {
		return source.split("..").map { it.trim().trimEnd('f', 'F').toFloat() }.let { it[0]..it[1] }
	}
}
