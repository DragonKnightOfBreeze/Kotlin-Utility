package com.windea.commons.kotlin.extension

import java.io.*

/**不包含扩展名的文件名。*/
val File.shotName: String get() = this.nameWithoutExtension

/**判断是否是文本文件。*/
val File.isTextFile: Boolean get() = TODO()


/**判断是否是图片文件。*/
val File.isImageFile: Boolean get() = TODO()

