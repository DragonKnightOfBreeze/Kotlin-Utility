package com.windea.utility.common.dsl

/**领域专用语言。*/
interface Dsl {
	/**生成对应的文本。*/
	fun generate(): String
	
	companion object
}

/**领域专用语言的配置。*/
interface DslConfig {
	companion object
}


typealias DslWrapper = Pair<String, String?>
