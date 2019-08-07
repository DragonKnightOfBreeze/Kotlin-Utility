import org.junit.*

class CommonTests {
	@Test
	fun test1() {
		println(1.0.toBigDecimal() - 0.9.toBigDecimal() == 0.1.toBigDecimal())
	}
	
	//中文方法名居然是合法的，什么鬼东西……
	@Test
	fun test2() {
		你好 { "世界！" }
	}
}

fun 你好(message: () -> String) {
	println("你好：")
	println(message())
}


