package com.windea.commons.kotlin.extension

import kotlin.math.pow

fun Int.pow(n: Int) = this.toFloat().pow(n).toInt()

fun Int.pow(n: Float) = this.toFloat().pow(n).toInt()


fun Float.coerceInAbs(value: Float) = this.coerceIn(-value, value)

fun Float.coerceIn01() = this.coerceIn(0f, 1f)

fun Double.coerceIn01() = this.coerceIn(0.0, 1.0)

fun Float.coerceInAbs1() = this.coerceIn(-1f, 1f)

fun Double.coerceInAbs1() = this.coerceIn(-1.0, 1.0)
