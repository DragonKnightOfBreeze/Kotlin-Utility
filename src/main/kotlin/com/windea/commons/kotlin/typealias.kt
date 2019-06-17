package com.windea.commons.kotlin

typealias MList<E> = MutableList<E>

typealias MSet<E> = MutableSet<E>

typealias MMap<K, V> = MutableMap<K, V>


fun <E> mListOf(vararg elements: E) = mutableListOf(*elements)

fun <E> mSetOf(vararg elements: E) = mutableSetOf(*elements)

fun <K, V> mMapOf(vararg pairs: Pair<K, V>) = mutableMapOf(*pairs)
