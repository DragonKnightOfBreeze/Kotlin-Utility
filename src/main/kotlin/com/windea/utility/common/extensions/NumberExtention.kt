package com.windea.utility.common.extensions

import kotlin.math.*

/**进行乘方运算。*/
fun Int.pow(n: Int) = this.toFloat().pow(n).toInt()

/**进行乘方运算。*/
fun Int.pow(n: Float) = this.toFloat().pow(n).toInt()


/**取当前数值在指定范围内的夹值。*/
infix fun Int.clamp(range: ClosedRange<Int>) = this.coerceIn(range)

/**取当前数值在指定范围内的夹值。*/
infix fun Long.clamp(range: ClosedRange<Long>) = this.coerceIn(range)

/**取当前数值在指定范围内的夹值。*/
infix fun Float.clamp(range: ClosedRange<Float>) = this.coerceIn(range)

/**取当前数值在指定范围内的夹值。*/
infix fun Double.clamp(range: ClosedRange<Double>) = this.coerceIn(range)


/**限制在指定的相反数之间。*/
fun Int.coerceInOps(value: Int) = this.coerceIn(-value, value)

/**限制在指定的相反数之间。*/
fun Long.coerceInOps(value: Long) = this.coerceIn(-value, value)

/**限制在指定的相反数之间。*/
fun Float.coerceInOps(value: Float) = this.coerceIn(-value, value)

/**限制在指定的相反数之间。*/
fun Double.coerceInOps(value: Double) = this.coerceIn(-value, value)


/**限制在指定0到1之间。*/
fun Float.coerceIn01() = this.coerceIn(0f, 1f)

/**限制在指定0到1之间。*/
fun Double.coerceIn01() = this.coerceIn(0.0, 1.0)

/**限制在-1到1之间。*/
fun Float.coerceInOps1() = this.coerceIn(-1f, 1f)

/**限制在-1到1之间。*/
fun Double.coerceInOps1() = this.coerceIn(-1.0, 1.0)
