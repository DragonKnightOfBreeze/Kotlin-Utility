package com.windea.commons.kotlin.extension

fun Float.coerceInAbs(value: Float) = this.coerceIn(-value, value)

fun Float.coerceIn01() = this.coerceIn(0f, 1f)

fun Double.coerceIn01() = this.coerceIn(0.0, 1.0)

fun Float.coerceInAbs1() = this.coerceIn(-1f, 1f)

fun Double.coerceInAbs1() = this.coerceIn(-1.0, 1.0)
