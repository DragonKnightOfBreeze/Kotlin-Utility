package com.windea.commons.kotlin.utils

/**
 * 数学的工具类。
 */
@Deprecated("使用标准库自带的拓展。")
object MathUtils {
	/**
	 * 夹值方法。
	 */
	fun clamp(number: Int, min: Int, max: Int): Int {
		return when {
			number in min..max -> number
			number < min -> min
			else -> max
		}
	}
	
	/**
	 * 夹值方法。
	 */
	fun clamp(number: Float, min: Float, max: Float): Float {
		return when {
			number in min..max -> number
			number < min -> min
			else -> max
		}
	}
	
	/**
	 * 夹值方法，从0到1。
	 */
	fun clamp01(number: Float): Float {
		return clamp(number, 0f, 1f)
	}
	
	/**
	 * 夹值方法，从-1到1。
	 */
	fun clampAbs1(number: Float, message: String? = null): Float {
		return clamp(number, -1f, 1f)
	}
}
