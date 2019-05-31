package com.windea.kotlin.utils

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import java.security.SecureRandom

/**
 * 随机数的工具类。
 */
object RandomUtils {
	private val log: Log = LogFactory.getLog(RandomUtils::class.java)
	private val random = SecureRandom()
	
	/**
	 * 得到0到指定范围内的随机数。包含上下限。
	 */
	fun range(max: Int): Int {
		return range(0, max)
	}
	
	/**
	 * 得到指定范围内的随机数。包含上下限。
	 */
	fun range(min: Int, max: Int): Int {
		if(min > max) {
			throw IllegalArgumentException("Min Value is greater than max Value.")
		}
		return min + random.nextInt(max + 1 - min)
	}
	
	/**
	 * 得到0到指定范围内的随机数。包含上下限。可指定精确度 [precision]。
	 */
	fun range(max: Float, precision: Int = 2): Float {
		return range(0f, max, precision)
	}
	
	/**
	 * 得到指定范围内的随机数。包含上下限。可指定精确度 [precision]。
	 */
	fun range(min: Float, max: Float, precision: Int = 2): Float {
		if(min > max) {
			throw IllegalArgumentException("Min Value is greater than max Value.")
		}
		val ratio = Math.pow(10.toDouble(), precision.toDouble()).toFloat()
		return range((min * ratio).toInt(), (max * ratio).toInt()) / ratio
	}
	
	/**
	 * 得到0到1的随机数。包含上下限。可指定精确度 [precision]。
	 */
	fun range01(precision: Int = 2): Float {
		return range(0f, 1f, precision)
	}
	
	/**
	 * 得到-1到1的随机数。包含上下限。可指定精确度 [precision]。
	 */
	fun rangeAbs1(precision: Int = 2): Float {
		return range(-1f, 1f, precision)
	}
	
	/**
	 * 得到以指定数值 [number] 为中心的浮动范围内的随机数。包含上下限。
	 */
	fun delta(number: Int, limit: Int): Int {
		return delta(number, limit, limit)
	}
	
	/**
	 * 得到以指定数值 [number] 为中心的浮动范围内的随机数。包含上下限。
	 */
	fun delta(number: Int, lowerLimit: Int, upperLimit: Int): Int {
		return number - lowerLimit + range(lowerLimit + upperLimit)
	}
	
	/**
	 * 得到以指定数值 [number] 为中心的浮动范围内的随机数。包含上下限。可指定精确度 [precision]。
	 */
	fun delta(number: Float, limit: Float, precision: Int = 2): Float {
		return delta(number, limit, limit, precision)
	}
	
	/**
	 * 得到以指定数值 [number] 为中心的浮动范围内的随机数。包含上下限。可指定精确度 [precision]。
	 */
	fun delta(number: Float, lowerLimit: Float, upperLimit: Float, precision: Int = 2): Float {
		val ratio = Math.pow(10.toDouble(), precision.toDouble()).toFloat()
		return delta((number * ratio).toInt(), (lowerLimit * ratio).toInt(), (upperLimit * ratio).toInt()) / ratio
	}
}
