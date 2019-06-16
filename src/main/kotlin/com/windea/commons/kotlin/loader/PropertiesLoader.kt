package com.windea.commons.kotlin.loader

import com.windea.commons.kotlin.loader.impl.StandardPropertiesLoader

interface PropertiesLoader : DataLoader {
	companion object {
		val instance = StandardPropertiesLoader()
	}
}
