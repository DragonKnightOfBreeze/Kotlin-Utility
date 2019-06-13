@file:Suppress("UNCHECKED_CAST")

package com.windea.commons.kotlin.generator.impl

import com.windea.commons.kotlin.generator.ITextGenerator
import com.windea.commons.kotlin.generator.Messages
import com.windea.commons.kotlin.utils.JsonUtils
import com.windea.commons.kotlin.utils.YamlUtils
import java.nio.file.Files
import java.nio.file.Path

/**
 * Intellij IDEA配置文件的生成器。
 */
class IdeaConfigGenerator : ITextGenerator {
	private val inputMap = mutableMapOf<String, Any?>()
	private var outputText = ""
	
	/**
	 * @param inputType JsonSchema, YamlSchema
	 */
	override fun from(inputPath: String, inputType: String): ITextGenerator {
		when(inputType) {
			"JsonSchema" -> this.inputMap += JsonUtils.fromFile(inputPath)
			"YamlSchema" -> this.inputMap += YamlUtils.fromFile(inputPath)
			else -> throw IllegalArgumentException(Messages.invalidInputType)
		}
		return this
	}
	
	
	/**
	 * @param generateStrategy YamlAnnotation
	 */
	override fun generate(generateStrategy: String): ITextGenerator {
		when(generateStrategy) {
			"YamlAnnotation" -> generateYamlAnnotation()
			else -> throw IllegalArgumentException(Messages.invalidGenerateStrategy)
		}
		return this
	}
	
	private fun generateYamlAnnotation() {
		val definitions = inputMap["definitions"] as Map<String, Map<String, Any?>>
		
		outputText += """
		|<!-- ${Messages.prefixComment} -->
		|<templateSet group="YamlAnnotation">
		|${definitions.map { (templateName, template) ->
			val description = template["description"]
			val params = when {
				"properties" in template -> template["properties"] as Map<String, Map<String, Any?>>
				else -> mapOf()
			}
			val paramSnippet = when {
				params.isNotEmpty() -> ": {${params.keys.joinToString(", ") { "$it: $$it$" }}}"
				else -> ""
			}
			
			//value的格式示例：@Scope: {scope: $scope$, ...}
			"""
			|  <template name="@$templateName" value="$templateName$paramSnippet"
		    |            description="$description"
		    |            toReformat="true" toShortenFQNames="true" useStaticImport="true">
			|${params.entries.joinToString("\n") { (paramName, param) ->
				val defaultValue = param.getOrDefault("default", "")
				
				"""
				|    <variable name="$paramName" expression="" defaultValue="&quot;$defaultValue&quot;" alwaysStopAt="true"/>
				""".trimMargin()
			}}
			|    <context>
			|      <option name="CSS" value="false"/>
			|      <option name="CUCUMBER_FEATURE_FILE" value="false"/>
			|      <option name="CoffeeScript" value="false"/>
			|      <option name="DART" value="false"/>
			|      <option name="ECMAScript6" value="false"/>
			|      <option name="HAML" value="false"/>
			|      <option name="HTML" value="false"/>
			|      <option name="Handlebars" value="false"/>
			|      <option name="JADE" value="false"/>
			|      <option name="JAVA_SCRIPT" value="false"/>
			|      <option name="JSON" value="false"/>
			|      <option name="JSP" value="false"/>
			|      <option name="OTHER" value="true"/>
			|      <option name="PL/SQL" value="false"/>
			|      <option name="REQUEST" value="false"/>
			|      <option name="SQL" value="false"/>
			|      <option name="TypeScript" value="false"/>
			|      <option name="Vue" value="false"/>
			|      <option name="XML" value="false"/>
			|    </context>
			|  </template>
			""".trimMargin()
		}.joinToString("\n\n")}
		|</templateSet>
		""".trimMargin()
	}
	
	
	/**
	 * @param outputType Default
	 */
	override fun to(outputPath: String, outputType: String) {
		when(outputType) {
			"Default" -> Files.writeString(Path.of(outputPath), outputText)
			else -> throw IllegalArgumentException(Messages.invalidOutputType)
		}
	}
	
	fun to(outputPath: String) = to(outputPath, "Default")
}