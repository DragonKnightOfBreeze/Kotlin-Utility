import com.windea.utility.common.dsl.data.*
import org.junit.*

class DslTests {
	@Test
	fun test1() {
		val str = xml {
			comment("123456")
			element("123") {
				element("abc", text = "abc")
			}
		}.generate()
		println(str)
	}
}
