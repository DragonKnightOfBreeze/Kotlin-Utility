package com.windea.utility.springboot.core.components.converters

import org.springframework.core.convert.converter.*
import org.springframework.stereotype.*

/**字符串到整数范围的转化器。*/
@Component
class StringToIntRangeConverter : Converter<String, IntRange> {
	override fun convert(source: String): IntRange? {
		return source.split("..").map { it.trim().toInt() }.let { it[0]..it[1] }
	}
}
