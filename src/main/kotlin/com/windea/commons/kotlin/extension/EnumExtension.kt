package com.windea.commons.kotlin.extension

/**判断两个枚举的枚举常量名是否相等。*/
infix fun Enum<*>.nameEquals(other: Enum<*>): Boolean {
	return this.name == other.name
}

/**判断两个枚举的枚举常量名是否相等。忽略显示格式（一般是PascalCase或SCREAMING_SNAKE_CASE）。*/
infix fun Enum<*>.nameEqualsIsc(other: Enum<*>): Boolean {
	return this.name equalsIsc other.name
}
