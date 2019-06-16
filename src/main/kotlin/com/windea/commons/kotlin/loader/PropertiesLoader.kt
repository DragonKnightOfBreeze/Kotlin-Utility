package com.windea.commons.kotlin.loader

import com.windea.commons.kotlin.loader.impl.StandardPropertiesLoader

/**
 * Properties数据读取器的接口。
 */
interface PropertiesLoader : DataLoader {
	companion object {
		val instance = StandardPropertiesLoader()
	}
}
