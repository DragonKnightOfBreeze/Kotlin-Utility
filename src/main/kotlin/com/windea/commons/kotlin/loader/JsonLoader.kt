package com.windea.commons.kotlin.loader

import com.windea.commons.kotlin.loader.impl.GsonJsonLoader


interface JsonLoader : DataLoader {
	companion object {
		val instance = GsonJsonLoader()
	}
}
