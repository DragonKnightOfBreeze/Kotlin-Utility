package com.windea.commons.kotlin.extension

import java.io.*

/**不包含扩展名在内的文件名。*/
val File.shotName: String get() = this.nameWithoutExtension
