package com.windea.utility.common.annotations.messages

import org.intellij.lang.annotations.*

/**本地化描述的注解。*/
@MustBeDocumented
@Repeatable
annotation class Description(
	@Language("Markdown")
	val text: String,
	val locale: String = "Chs"
)



