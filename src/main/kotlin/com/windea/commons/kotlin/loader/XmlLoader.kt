package com.windea.commons.kotlin.loader

import com.windea.commons.kotlin.loader.impl.StandardXmlLoader

interface XmlLoader : DataLoader {
	companion object {
		val instance = StandardXmlLoader()
	}
}
