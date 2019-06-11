import com.windea.commons.kotlin.generator.impl.IdeaConfigGenerator

fun main() {
	IdeaConfigGenerator.fromJsonSchema("D:\\My Documents\\My Projects\\Java Projects\\Utility\\Kotlin Utility\\src\\main\\resources\\test.json")
		.execute().generate("D:\\My Documents\\My Projects\\Java Projects\\Utility\\Kotlin Utility\\src\\main\\resources\\Yaml.xml")
}
