package com.windea.utility.common.extensions

import kotlin.math.*

/**进行乘方运算。*/
fun Int.pow(n: Int) = this.toFloat().pow(n).toInt()

/**进行乘方运算。*/
fun Int.pow(n: Float) = this.toFloat().pow(n)

/**进行乘方运算。*/
fun Int.pow(n: Double) = this.toDouble().pow(n)


/**限定在0和1之间。*/
fun Float.coerceIn() = this.coerceIn(0f, 1f)

/**限定在0和1之间。*/
fun Double.coerceIn() = this.coerceIn(0.0, 1.0)


/**限制在指定的相反数之间。*/
fun Int.coerceInOps(value: Int) = this.coerceIn(-value, value)

/**限制在指定的相反数之间。*/
fun Long.coerceInOps(value: Long) = this.coerceIn(-value, value)

/**限制在指定的相反数之间。默认为-1和1。*/
fun Float.coerceInOps(value: Float = 1f) = this.coerceIn(-value, value)

/**限制在指定的相反数之间。默认为-1和1。*/
fun Double.coerceInOps(value: Double = 1.0) = this.coerceIn(-value, value)
