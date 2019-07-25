import com.windea.commons.kotlin.extension.*
import org.junit.*

class StringExtensionTests {
	//经测试，String.split()返回的字符串列表长度是不固定的
	
	@Test
	fun test1() {
		val str = "http://localhost:8080/www.github.com/DragonKnightOfBreeze"
		val strList = str.substringOrEmpty("://", ":", "/")
		println(strList)
	}
	
	@Test
	fun test2() {
		val str = "http://www.github.com/DragonKnightOfBreeze"
		val strList = str.substringOrEmpty("://", ":", "/")
		println(strList)
	}
	
	@Test
	fun test3() {
		val str = "www.github.com/DragonKnightOfBreeze"
		val strList = str.substringOrEmpty("://", ":", "/")
		println(strList)
	}
	
	@Test
	fun test4() {
		val str = "http://localhost:8080/www.github.com/DragonKnightOfBreeze"
		val strList = str.substringOrEmpty(":", "/", "://")
		println(strList)
	}
	
	@Test
	fun test5() {
		val str = "www.github.com"
		val strList = str.substringWithDefault("://", ":", "/") { listOf("http", "localhost", "8080", it) }
		println(strList)
	}
	
	@Test
	fun test6() {
		val str = "c://Documents/MyBook.txt"
		println(str.toPathInfo())
	}
	
	@Test
	fun test7() {
		val str = "http://localhost:8080/www.github.com/DragonKnightOfBreeze?name=Windea&Weapon=BreezesLanding&Weapon=BreathOfBreeze"
		println(str.toUrlInfo())
	}
}
