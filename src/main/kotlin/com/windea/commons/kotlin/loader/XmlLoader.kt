package com.windea.commons.kotlin.loader

import com.windea.commons.kotlin.loader.impl.StandardXmlLoader

/**
 * Xml数据读取器的接口。
 */
interface XmlLoader : DataLoader {
	companion object {
		@JvmStatic
		fun instance(): StandardXmlLoader {
			return StandardXmlLoader()
		}
	}
}
