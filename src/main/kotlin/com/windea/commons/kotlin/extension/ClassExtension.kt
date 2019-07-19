package com.windea.commons.kotlin.extension

import java.io.*
import java.lang.reflect.*

/**判断是否是字符序列。*/
fun <T> Class<T>.isCharSequence(): Boolean {
	return when {
		CharSequence::class.java == this -> true
		else -> this.interfaces.any { it.isCharSequence() } || this.superclass?.isCharSequence() ?: false
	}
}

/**判断是否是字符串。*/
fun <T> Class<T>.isString(): Boolean {
	return String::class.java == this
}

/**判断是否是可迭代类/接口。*/
fun <T> Class<T>.isIterable(): Boolean {
	return when {
		Iterable::class.java == this -> true
		else -> this.interfaces.any { it.isIterable() } || this.superclass?.isIterable() ?: false
	}
}

/**判断是否是列表。*/
fun <T> Class<T>.isList(): Boolean {
	return when {
		List::class.java == this -> true
		else -> this.interfaces.any { it.isList() } || this.superclass?.isList() ?: false
	}
}

/**判断是否是集。*/
fun <T> Class<T>.isSet(): Boolean {
	return when {
		Set::class.java == this -> true
		else -> this.interfaces.any { it.isSet() } || this.superclass?.isSet() ?: false
	}
}

/**判断是否是映射。*/
fun <T> Class<T>.isMap(): Boolean {
	return when {
		Map::class.java == this -> true
		else -> this.interfaces.any { it.isMap() } || this.superclass?.isMap() ?: false
	}
}

/**判断是否是可序列类/接口。*/
fun <T> Class<T>.isSerializable(): Boolean {
	return when {
		Serializable::class.java == this -> true
		else -> this.interfaces.any { it.isSerializable() } || this.superclass?.isSerializable() ?: false
	}
}


/**得到类型的属性名-取值方法映射。*/
fun <T> Class<T>.getGetterMap(): Map<String, Method> {
	return this.methods.filter { it.name.startsWith("get") }.associateBy { it.name.substring(3).firstCharToLowerCase() }
}

/**得到类型的属性名-赋值方法映射。*/
fun <T> Class<T>.getSetterMap(): Map<String, Method> {
	return this.methods.filter { it.name.startsWith("set") }.associateBy { it.name.substring(3).firstCharToLowerCase() }
}
