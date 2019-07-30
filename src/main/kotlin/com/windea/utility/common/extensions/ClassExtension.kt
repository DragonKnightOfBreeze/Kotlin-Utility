package com.windea.utility.common.extensions

import java.io.*
import java.lang.reflect.*

/**判断是否是字符序列。*/
val <T> Class<T>.isCharSequence: Boolean get() = this.checkInterface(CharSequence::class.java)

/**判断是否是字符串。*/
val <T> Class<T>.isString: Boolean get() = this == String::class.java

/**判断是否是可迭代类/接口。*/
val <T> Class<T>.isIterable: Boolean get() = this.checkInterface(Iterable::class.java)

/**判断是否是列表。*/
val <T> Class<T>.isList: Boolean get() = this.checkInterface(List::class.java)

/**判断是否是集。*/
val <T> Class<T>.isSet: Boolean get() = this.checkInterface(Set::class.java)

/**判断是否是映射。*/
val <T> Class<T>.isMap: Boolean get() = this.checkInterface(Map::class.java)

/**判断是否是可序列类/接口。*/
val <T> Class<T>.isSerializable: Boolean get() = this.checkInterface(Serializable::class.java)

private tailrec fun Class<*>.checkClass(clazz: Class<*>): Boolean {
	if(this != clazz || this.superclass == null) return false
	return superclass.checkClass(clazz)
}

private fun Class<*>.checkInterface(clazz: Class<*>): Boolean {
	if(this != clazz || this.superclass == null) return false
	return this.interfaces.any { it.checkInterface(clazz) } || superclass.checkInterface(clazz)
}


/**得到类型的属性名-取值方法映射。*/
val <T> Class<T>.getterMap: Map<String, Method>
	get() = this.methods.filter { it.name.startsWith("get") }.associateBy { it.name.substring(3).firstCharToLowerCase() }


/**得到类型的属性名-赋值方法映射。*/
val <T> Class<T>.setterMap: Map<String, Method>
	get() = this.methods.filter { it.name.startsWith("set") }.associateBy { it.name.substring(3).firstCharToLowerCase() }
