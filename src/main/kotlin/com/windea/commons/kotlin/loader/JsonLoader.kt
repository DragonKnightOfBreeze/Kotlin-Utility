package com.windea.commons.kotlin.loader

import com.windea.commons.kotlin.loader.impl.GsonJsonLoader

/**
 * Json数据读取器的接口。
 */
interface JsonLoader : DataLoader {
	companion object {
		@JvmStatic
		fun instance(): GsonJsonLoader {
			return GsonJsonLoader()
		}
	}
}
