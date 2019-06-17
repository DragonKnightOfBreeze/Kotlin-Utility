import com.windea.commons.kotlin.loader.JsonLoader
import com.windea.commons.kotlin.loader.YamlLoader

fun main() {
	//TESTED
	val json = "{'a':1,'b':2}"
	val jsonMap1 = JsonLoader.instance.fromString(json)
	println(jsonMap1)
	
	//TESTED
	val jsonPath = "D:\\My Documents\\My Projects\\Java Projects\\Utility\\Kotlin Utility\\src\\main\\resources\\test.json"
	val jsonMap2 = JsonLoader.instance.fromFile(jsonPath)
	println(jsonMap2)
	
	//TESTED
	val jsonString = JsonLoader.instance.toString(jsonMap2)
	println(jsonString)
	
	//TESTED 使用File(path).writeText(string)
	val jsonData = mapOf("aaaa" to 1)
	JsonLoader.instance.toFile(jsonData, jsonPath)
	
	//TESTED
	val yaml = "{a: 1}"
	val yamlMap1 = YamlLoader.instance.fromString(yaml)
	println(yamlMap1)
	
	//TESTED
	val yamlPath = "D:\\My Documents\\My Projects\\Java Projects\\Utility\\Kotlin Utility\\src\\main\\resources\\test.yml"
	val yamlMap2 = YamlLoader.instance.fromFile(yamlPath)
	println(yamlMap2)
	
	//TESTED
	val yamlString = YamlLoader.instance.toString(yamlMap2)
	println(yamlString)
	
	//TESTED
	val yamlData = mapOf("aaaa" to 1)
	YamlLoader.instance.toFile(yamlData, yamlPath)
}
