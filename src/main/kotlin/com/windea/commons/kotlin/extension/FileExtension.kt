package com.windea.commons.kotlin.extension

import java.io.*

/**不包含扩展名在内的文件名。*/
val File.shotName: String get() = this.nameWithoutExtension

/**判断是否是文本文件。*/
fun File.isTextFile(): Boolean {
	TODO()
}

/**判断是否是图片文件。*/
fun File.isImageFile(): Boolean {
	TODO()
}

