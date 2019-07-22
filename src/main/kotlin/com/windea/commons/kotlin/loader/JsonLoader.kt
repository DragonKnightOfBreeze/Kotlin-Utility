package com.windea.commons.kotlin.loader

import com.windea.commons.kotlin.loader.impl.*

/**Json数据读取器的接口。*/
interface JsonLoader : DataLoader {
	companion object {
		/**得到一个单例实例。*/
		val instance = GsonJsonLoader()
	}
}
