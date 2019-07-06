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


fun main() {
	val obj = Test().testFunc<MyClass>("123")
	println(obj)
	println(obj.javaClass)
	println(obj.value)
}
