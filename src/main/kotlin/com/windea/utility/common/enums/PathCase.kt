package com.windea.utility.common.enums

import com.windea.utility.common.annotations.marks.*

/**路径的显示格式。*/
@NotTested
enum class PathCase(
	val regex: Regex
) {
	Unknown("^$".toRegex()),
	/**
	 * Windows路径。
	 *
	 * 示例：`Directory\\FileName.ext`。
	 */
	WindowsPath("^\\.?\\.?(?:\\\\\\\\)?(.+)(?:\\\\\\\\(.+))*$".toRegex()),
	/**
	 * Unix路径。
	 *
	 * 示例：`Directory/FileName.ext`。
	 */
	UnixPath("^\\.?\\.?(?:/)?(.+)(?:/(.+))*$".toRegex()),
	/**
	 * 引用路径。
	 *
	 * 示例：`Reference.path[0]`。
	 *
	 * * `Reference` 表示一个对象/属性/映射的值。
	 * * `[0]` 表示一个列表的元素。
	 */
	ReferencePath("^([a-zA-Z_])(?:(?:\\.([a-zA-Z_]))|(?:\\[(\\d+)]))*$".toRegex()),
	/**
	 * Json路径。
	 *
	 * 示例：`#/{Category}/0/Name`。
	 *
	 * * `{}` 表示一个对象/映射。
	 * * `{Category}` 表示一个注为指定占位符的对象/映射。
	 * * `/.*Name/` 表示一个匹配指定正则的对象/映射。
	 * * `Name` 表示一个对象/映射的属性/键。
	 * * `[]` 表示一个列表。
	 * * `1..10` 表示一个列表的指定范围的切片。包含上下限。
	 * * `1` 表示一个列表的索引。
	 */
	JsonPath("^#(?:/(.+))+$".toRegex())
}
