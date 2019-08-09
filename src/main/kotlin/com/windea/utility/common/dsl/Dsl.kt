package com.windea.utility.common.dsl

import com.windea.utility.common.annotations.marks.*

/**领域专用语言。*/
@NotSuitable("保存行内元素的结构信息")
interface Dsl {
	companion object
}

/**领域专用语言的配置。*/
interface DslConfig {
	companion object
}


typealias DslWrapper = Pair<String, String?>
