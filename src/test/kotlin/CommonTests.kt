import com.windea.utility.common.extensions.*
import org.junit.*
import java.time.*

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
	
	fun 你好(message: () -> String) {
		println("你好：")
		println(message())
	}
	
	@Test
	fun test3() {
		val 昨天: Period = Period.of(2019, 8, 7)
		
		昨天 是 (1 天 前)
	}
	
	infix fun <T> T.是(other: T) = this == other
	
	object 前
	
	infix fun Int.天(other: 前) = this.days
}