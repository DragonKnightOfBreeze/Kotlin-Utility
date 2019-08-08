import com.windea.utility.common.dsl.*
import com.windea.utility.common.dsl.data.*
import com.windea.utility.common.dsl.text.*
import org.junit.*

class DslTests {
	@Test
	fun xmlDslTest() {
		val str = Dsl.xml {
			comment("123456")
			element("123", "a" to 1) {
				comment("333")
				element("abc", text = "abc")
			}
		}.generate()
		//<!--123456-->
		//<123 a="1">
		//  <!--333-->
		//  <abc>abc</abc>
		//</123>
		println(str)
	}
	
	@Test
	fun starBoundTextDslTest() {
		val str = Dsl.starBoundText {
			"${pht("player_name")}：这是一段${ct("blue") { "彩色" }}文本。"
		}.generate()
		//<player_name>：这是一段^blue;彩色^reset;文本。
		println(str)
	}
}
