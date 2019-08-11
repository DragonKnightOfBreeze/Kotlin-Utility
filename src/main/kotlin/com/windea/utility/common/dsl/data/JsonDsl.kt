package com.windea.utility.common.dsl.data

import com.windea.utility.common.annotations.marks.*
import com.windea.utility.common.dsl.*
import com.windea.utility.common.dsl.data.JsonDslConfig.indent
import com.windea.utility.common.dsl.data.JsonDslConfig.quote
import com.windea.utility.common.extensions.*
import java.lang.annotation.*

/**Json的领域专用语言。*/
@NotTested
@NotRecommended("可以直接从集合结构生成Json文本")
data class JsonDsl(
	var collection: JsonCollection<*> = JsonObject()
) : Dsl, JsonDslElement {
	override fun toString(): String {
		return collection.toString()
	}
}

/**Json领域专用语言的配置。*/
object JsonDslConfig {
	var indentSize: Int = 2
		set(value) {
			field = value.coerceIn(2, 8)
		}
	var preferDoubleQuote: Boolean = true
	
	internal val indent get() = " ".repeat(indentSize)
	internal val quote get() = if(preferDoubleQuote) "\"" else "'"
}


/**扩展的Json功能。*/
@MustBeDocumented
@Inherited
annotation class ExtendedJsonFeature


/**Json领域专用语言的元素。*/
interface JsonDslElement

/**Json领域专用语言的可换行元素。*/
interface JsonDslNewLineElement : JsonDslElement {
	var newLine: Boolean
}

/**Json领域专用语言的项元素。*/
interface JsonItem<T> : JsonDslElement {
	val value: T
}

/**Json领域专用语言的集合元素。*/
interface JsonCollection<T : MutableList<*>> : JsonItem<T> {
	operator fun T.plus(item: T) = item
}

/**Json领域专用语言的可以空行分割内容的元素。*/
interface JsonDslBlankLineElement : JsonDslElement {
	var blankLineSize: Int
}


/**Json布尔项。*/
inline class JsonBoolean(
	override val value: Boolean
) : JsonItem<Boolean> {
	override fun toString(): String {
		return value.toString()
	}
}

/**Json整数项。*/
inline class JsonInteger(
	override val value: Int
) : JsonItem<Int> {
	override fun toString(): String {
		return value.toString()
	}
}

/**Json数值项。*/
inline class JsonNumber(
	override val value: Float
) : JsonItem<Float> {
	override fun toString(): String {
		return value.toString()
	}
}

/**Json字符串项。*/
inline class JsonString(
	override val value: String
) : JsonItem<String> {
	override fun toString(): String {
		return "$quote${value.unescape()}$quote"
	}
}

/**Json属性。*/
data class JsonProperty<T>(
	val key: String,
	val value: JsonItem<T>?
) : JsonDslElement {
	override fun toString(): String {
		return "$quote$key$quote: $value"
	}
}


/**Json数组。*/
data class JsonArray(
	override val value: MutableList<JsonItem<*>?> = mutableListOf(),
	override var newLine: Boolean = true,
	override var blankLineSize: Int = 0
) : JsonCollection<MutableList<JsonItem<*>?>>, JsonDslNewLineElement, JsonDslBlankLineElement {
	override fun toString(): String {
		val prefix = if(newLine) "[\n" else "["
		val delimiter = if(newLine) ",\n${"\n".repeat(blankLineSize)}" else ", "
		val suffix = if(newLine) "\n]" else "]"
		return value.joinToString(delimiter, prefix, suffix) { "$indent$it" }
	}
}

/**Json对象。*/
data class JsonObject(
	override val value: MutableList<JsonProperty<*>> = mutableListOf(),
	override var newLine: Boolean = true,
	override var blankLineSize: Int = 0
) : JsonCollection<MutableList<JsonProperty<*>>>, JsonDslNewLineElement, JsonDslBlankLineElement {
	override fun toString(): String {
		val prefix = if(newLine) "{\n" else "}"
		val delimiter = if(newLine) ",\n${"\n".repeat(blankLineSize)}" else ", "
		val suffix = if(newLine) "\n}" else "}"
		return value.joinToString(delimiter, prefix, suffix) { "$indent$it" }
	}
}


/**构建Xml的领域专用语言。*/
fun Dsl.Companion.json(content: JsonDsl.() -> Unit) = JsonDsl().also { it.content() }

/**配置xml的领域专用语言。*/
fun DslConfig.Companion.json(config: JsonDslConfig.() -> Unit) = JsonDslConfig.config()


fun JsonDsl.array(value: JsonArray.() -> Unit) = JsonArray().also { it.value() }.also { this.collection = it }

fun JsonDsl.obj(value: JsonObject.() -> Unit) = JsonObject().also { it.value() }.also { this.collection = it }


fun JsonArray.item() = run { this.value.add(null) }

fun JsonArray.item(value: Boolean) = JsonBoolean(value).also { this.value += it }

fun JsonArray.item(value: Int) = JsonInteger(value).also { this.value += it }

fun JsonArray.item(value: Float) = JsonNumber(value).also { this.value += it }

fun JsonArray.item(value: String) = JsonString(value).also { this.value += it }

fun JsonArray.array(value: JsonArray.() -> Unit) = JsonArray().also { it.value() }.also { this.value += it }

fun JsonArray.obj(value: JsonObject.() -> Unit) = JsonObject().also { it.value() }.also { this.value += it }


fun JsonObject.prop(key: String) = run { this.value += JsonProperty<Any?>(key, null) }

fun JsonObject.prop(key: String, value: Boolean) = JsonProperty(key, JsonBoolean(value)).also { this.value += it }

fun JsonObject.prop(key: String, value: Int) = JsonProperty(key, JsonInteger(value)).also { this.value += it }

fun JsonObject.prop(key: String, value: Float) = JsonProperty(key, JsonNumber(value)).also { this.value += it }

fun JsonObject.prop(key: String, value: String) = JsonProperty(key, JsonString(value)).also { this.value += it }

fun JsonObject.arrayProp(key: String, value: JsonArray.() -> Unit) = JsonProperty(key, JsonArray().also { it.value() }).also { this.value += it }

fun JsonObject.objProp(key: String, value: JsonObject.() -> Unit) = JsonProperty(key, JsonObject().also { it.value() }).also { this.value += it }


/**配置当前元素的换行。默认换行。*/
fun <T : JsonDslNewLineElement> T.n(newLine: Boolean = true): T {
	return this.also { it.newLine = newLine }
}

/**配置当前元素的内容间空行数量。默认为1。*/
fun <T : JsonDslBlankLineElement> T.bn(blankLineSize: Int = 1): T {
	return this.also { it.blankLineSize = blankLineSize }
}
