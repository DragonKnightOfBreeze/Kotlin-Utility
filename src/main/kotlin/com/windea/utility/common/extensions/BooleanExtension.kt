package com.windea.utility.common.extensions

/**转化为指定的正数或负数。*/
fun Boolean.toOps(value: Int) = if(this) value else -value

/**转化为指定的正数或负数。*/
fun Boolean.toOps(value: Float) = if(this) value else -value

/**转化为指定的正数或负数。*/
fun Boolean.toOps(value: Double) = if(this) value else -value


/**转化为1或0。*/
fun Boolean.to01() = if(this) 1 else 0

/**转化为1或-1。*/
fun Boolean.toOps1() = if(this) 1 else -1
