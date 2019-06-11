@file:Suppress("UNCHECKED_CAST")

package com.windea.commons.kotlin.generator.impl

import com.windea.commons.kotlin.generator.ITextGenerator
import com.windea.commons.kotlin.utils.JsonUtils
import com.windea.commons.kotlin.utils.YamlUtils
import java.nio.file.Files
import java.nio.file.Path

/**
 * Intellij IDEA配置文件的生成器。
 */
class IdeaConfigGenerator : ITextGenerator {
	private val inputMap = mutableMapOf<String, Any?>()
	private var outputText = "<!-- Generated from kotlin script written by DragonKnightOfBreeze. -->\n"
	
	
	override fun execute(): IdeaConfigGenerator {
		generateYamlAnnotation()
		return this
	}
	
	private fun generateYamlAnnotation() {
		val definitions = inputMap["definitions"] as Map<String, Map<String, Any?>>
		
		outputText += """
		|<templateSet group="YamlAnnotation">
		|${definitions.map { (templateName, template) ->
			val description = template["description"]
			val params = when {
				"properties" in template -> template["properties"] as Map<String, Map<String, Any?>>
				else -> mapOf()
			}
			//TODO 允许自定义格式
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
	
	override fun generate(outputPath: String) {
		Files.writeString(Path.of(outputPath), outputText)
	}
	
	
	companion object {
		/**
		 * 从指定路径 [inputPath] 的json schema文件读取输入映射。
		 */
		@JvmStatic
		fun fromJsonSchema(inputPath: String): IdeaConfigGenerator {
			val generator = IdeaConfigGenerator()
			generator.inputMap += JsonUtils.fromFile(inputPath)
			return generator
		}
		
		/**
		 * 从指定路径 [inputPath] 的yaml schema文件读取输入映射。
		 */
		@JvmStatic
		fun fromYamlSchema(inputPath: String): IdeaConfigGenerator {
			val generator = IdeaConfigGenerator()
			generator.inputMap += YamlUtils.fromFile(inputPath)
			return generator
		}
	}
}
