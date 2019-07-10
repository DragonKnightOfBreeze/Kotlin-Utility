package com.windea.commons.kotlin.extension

fun Boolean.toAbs(value: Int) = if(this) value else -value

fun Boolean.toAbs(value: Float) = if(this) value else -value

fun Boolean.toAbs(value: Double) = if(this) value else -value

fun Boolean.to01() = if(this) 1 else -1

fun Boolean.toAbs1() = if(this) 1 else -1
