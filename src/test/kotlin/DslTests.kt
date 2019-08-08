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
				element("abc", text = "123")
			}
		}.toString()
		//<!--123456-->
		//<123 a="1">
		//  <!--333-->
		//  <abc>abc</abc>
		//</123>
		println(str)
	}
	
	@Test
	fun starBoundTextDslTest() {
		//可以使用dsl，也可以使用模版字符串。
		
		val str = Dsl.starBoundTextString {
			"${pht("player_name")}：这是一段${ct("blue") { "彩色" }}文本。"
		}
		//<player_name>：这是一段^blue;彩色^reset;文本。
		println(str)
		
		val dsl = Dsl.starBoundText {
			pht("player_name")
			t { "：这是一段" }
			ct("blue") { "彩色" }
			t { "文本" }
		}
		//<player_name>：这是一段^blue;彩色^reset;文本。
		println(dsl)
	}
}
