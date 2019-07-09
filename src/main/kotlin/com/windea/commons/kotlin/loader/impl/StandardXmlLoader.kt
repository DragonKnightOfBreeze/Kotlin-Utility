package com.windea.commons.kotlin.loader.impl

import com.windea.commons.kotlin.annotation.NotTested
import com.windea.commons.kotlin.loader.XmlLoader

@NotTested
class StandardXmlLoader : XmlLoader {
	override fun <T : Any> fromFile(path: String, type: Class<T>): T {
		TODO("not implemented")
	}
	
	override fun fromFile(path: String): Map<String, Any?> {
		TODO("not implemented")
	}
	
	override fun <T : Any> fromString(string: String, type: Class<T>): T {
		TODO("not implemented")
	}
	
	override fun fromString(string: String): Map<String, Any?> {
		TODO("not implemented")
	}
	
	override fun <T : Any> toFile(data: T, path: String) {
		TODO("not implemented")
	}
	
	override fun <T : Any> toString(data: T): String {
		TODO("not implemented")
	}
}
