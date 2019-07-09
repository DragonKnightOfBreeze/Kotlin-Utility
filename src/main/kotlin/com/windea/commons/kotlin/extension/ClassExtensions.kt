package com.windea.commons.kotlin.extension

import java.io.Serializable

fun <T> Class<T>.isCharSequence(): Boolean {
	return when {
		CharSequence::class.java == this -> true
		else -> this.interfaces.any { it.isCharSequence() } || this.superclass?.isCharSequence() ?: false
	}
}

fun <T> Class<T>.isString(): Boolean {
	return String::class.java.let { it == this }
}

fun <T> Class<T>.isIterable(): Boolean {
	return when {
		Iterable::class.java == this -> true
		else -> this.interfaces.any { it.isIterable() } || this.superclass?.isIterable() ?: false
	}
}

fun <T> Class<T>.isList(): Boolean {
	return when {
		List::class.java == this -> true
		else -> this.interfaces.any { it.isList() } || this.superclass?.isList() ?: false
	}
}

fun <T> Class<T>.isSet(): Boolean {
	return when {
		Set::class.java == this -> true
		else -> this.interfaces.any { it.isSet() } || this.superclass?.isSet() ?: false
	}
}

fun <T> Class<T>.isMap(): Boolean {
	return when {
		Map::class.java == this -> true
		else -> this.interfaces.any { it.isMap() } || this.superclass?.isMap() ?: false
	}
}

fun <T> Class<T>.isSerializable(): Boolean {
	return when {
		Serializable::class.java == this -> true
		else -> this.interfaces.any { it.isSerializable() } || this.superclass?.isSerializable() ?: false
	}
}
