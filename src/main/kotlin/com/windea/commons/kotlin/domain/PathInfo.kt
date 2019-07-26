package com.windea.commons.kotlin.domain

import java.nio.file.*

/**路径信息。相比[Path]更加轻量，同时也能进行解构。*/
data class PathInfo(
	/**文件路径。*/
	val path: String,
	/**文件根路径。*/
	val rootPath: String,
	/**文件所在文件夹。*/
	val fileDirectory: String,
	/**文件名。*/
	val fileName: String,
	/**不包含扩展名在内的文件名。*/
	val fileShotName: String,
	/**包含"."的文件扩展名。*/
	val fileExtension: String
) {
	/**是否存在上一级文件夹。*/
	val hasFileDirectory = fileDirectory.isNotEmpty()
	/**是否存在文件扩展名。*/
	val hasFileExtension = fileExtension.isNotEmpty()
}
