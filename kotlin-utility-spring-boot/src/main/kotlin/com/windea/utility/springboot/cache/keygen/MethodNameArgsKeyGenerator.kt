package com.windea.utility.springboot.cache.keygen

import com.windea.utility.common.extensions.*
import org.springframework.cache.interceptor.*
import java.lang.reflect.*

/**
 * 基于方法名和参数的缓存键生成器。
 *
 * * 生成的键形如url。
 * * 示例："/findAll", "/findByName?name=Windea"
 **/
class MethodNameArgsKeyGenerator : KeyGenerator {
	override fun generate(target: Any, method: Method, vararg params: Any?): Any {
		val path = "/${method.name}"
		val query = (method.parameters.map { it.name } zip params).joinToString("&") { (k, v) -> "$k=$v" }
		return "$path${query.ifNotEmpty { "?$it" }}"
	}
}
