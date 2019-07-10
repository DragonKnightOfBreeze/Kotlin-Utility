package com.windea.commons.kotlin.extension

object SystemExtension {
	/**
	 * 项目的实际工作路径。
	 */
	val workDirectory get() = System.getProperty("user.dir") ?: ""
	
	/**、
	 * 项目的实际工作平台名。例如：Windows 10。
	 */
	val desktopName get() = System.getProperty("os.name") ?: ""
	
	/**
	 * 项目工作环境的用户名。
	 */
	val userName get() = System.getProperty("user.name") ?: ""
	
	/**
	 * 项目工作环境的用户所在国家。
	 */
	val userCountry get() = System.getProperty("user.country") ?: ""
	
	/**
	 * 项目工作环境的用户所用语言。
	 */
	val userLanguage get() = System.getProperty("user.language") ?: ""
	
	/**
	 * 项目工作环境的文件分隔符。
	 */
	val fileSeparator get() = System.getProperty("file.separator") ?: ""
	
	/**
	 * 项目工作环境的文件编码。
	 */
	val fileEncoding get() = System.getProperty("file.encoding") ?: ""
	
	/**
	 * 项目工作环境的行分隔符。
	 */
	val lineSeparator get() = System.getProperty("line.separator") ?: ""
}
