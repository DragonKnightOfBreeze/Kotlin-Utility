package com.windea.commons.kotlin.extension

/**转化为指定的正数或负数。*/
fun Boolean.toAbs(value: Int) = if(this) value else -value

/**转化为指定的正数或负数。*/
fun Boolean.toAbs(value: Float) = if(this) value else -value

/**转化为指定的正数或负数。*/
fun Boolean.toAbs(value: Double) = if(this) value else -value

/**转化为1或0。*/
fun Boolean.to01() = if(this) 1 else 0

/**转化为1或-1。*/
fun Boolean.toAbs1() = if(this) 1 else -1
