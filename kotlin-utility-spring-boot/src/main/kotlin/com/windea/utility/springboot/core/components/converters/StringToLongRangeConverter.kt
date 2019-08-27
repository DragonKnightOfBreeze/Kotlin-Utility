package com.windea.utility.springboot.core.components.converters

import org.springframework.core.convert.converter.*
import org.springframework.stereotype.*

/**字符串到长整数范围的转化器。*/
@Component
class StringToLongRangeConverter : Converter<String, LongRange> {
	override fun convert(source: String): LongRange? {
		return source.split("..").map { it.trim().trimEnd('l', 'L').toLong() }.let { it[0]..it[1] }
	}
}
