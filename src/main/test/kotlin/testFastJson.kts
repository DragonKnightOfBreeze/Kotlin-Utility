import com.windea.commons.kotlin.loader.JsonLoader
import com.windea.commons.kotlin.loader.YamlLoader
import java.sql.Time

//使用jdk11，会报NoClassDefError
//使用jdk8则不会报错
//抛弃jdk新特性，改为使用kotlin本身的……

val sqlTime = Time(123456)
println(sqlTime)

//language=Json
val json = """
{"a":1,"b":2}
""".trimIndent()

val jsonMap = JsonLoader.instance.fromString(json)
println(jsonMap)

//language=Yaml
val yaml = """
a: 1
b: 2
""".trimIndent()
val yamlMap = YamlLoader.instance.fromString(yaml)
println(yamlMap)
