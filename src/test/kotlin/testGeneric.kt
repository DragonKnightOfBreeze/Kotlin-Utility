import com.windea.commons.kotlin.extension.mapToObject
import java.io.Serializable

class Test {
	fun <T> testFunc(text: String): T {
		return MyClass2(text) as T
	}
}

open class MyClass constructor(
	val value: String
)

class MyClass2 constructor(
	val value2: String
) : MyClass(value2)


class User : Serializable {
	var username: String = ""
	var password: String = ""
	var age: Int = 0
}

fun main() {
	val userMap = mapOf("username" to "abc", "password" to "123", "age" to 20)
	val user = userMap.mapToObject(User::class.java)
	println(user.username)
	println(user.password)
	println(user.age)
	
	// val obj = Test().testFunc<MyClass>("123")
	// println(obj)
	// println(obj.javaClass)
	// println(obj.value)
}
