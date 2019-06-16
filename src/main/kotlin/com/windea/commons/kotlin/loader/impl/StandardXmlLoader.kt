package com.windea.commons.kotlin.loader.impl

import com.windea.commons.kotlin.loader.XmlLoader

class StandardXmlLoader : XmlLoader {
	override fun <T> fromFile(path: String, type: Class<T>): T {
		TODO("not implemented")
	}
	
	override fun fromFile(path: String): Map<String, Any?> {
		TODO("not implemented")
	}
	
	override fun <T> fromString(string: String, type: Class<T>): T {
		TODO("not implemented")
	}
	
	override fun fromString(string: String): Map<String, Any?> {
		TODO("not implemented")
	}
	
	override fun <T> toFile(data: T, path: String) {
		TODO("not implemented")
	}
	
	override fun <T> toString(data: T): String {
		TODO("not implemented")
	}
}
