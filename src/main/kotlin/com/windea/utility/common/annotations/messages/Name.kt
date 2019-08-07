package com.windea.utility.common.annotations.messages


/**本地化名字的注解。*/
@MustBeDocumented
@Repeatable
annotation class Name(
	val text: String,
	val locale: String = "Chs"
)
