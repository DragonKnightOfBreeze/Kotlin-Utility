package com.windea.commons.kotlin.loader

import com.windea.commons.kotlin.loader.impl.*

/**Xml数据读取器的接口。*/
interface XmlLoader : DataLoader {
	companion object {
		/**得到一个实例。*/
		@JvmStatic
		fun instance(): JacksonXmlLoader {
			return JacksonXmlLoader()
		}
	}
}
