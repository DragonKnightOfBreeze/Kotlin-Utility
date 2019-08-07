package com.windea.utility.common.dsl

/**Dsl（领域专用语言）的接口。*/
interface Dsl {
	/**生成对应的文本。*/
	fun generate(): String
}
