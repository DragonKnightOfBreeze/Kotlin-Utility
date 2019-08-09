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
				comment("333").n()
				element("abc")
				element("a") { text("text") }
				element("a") { +"text2" }.un()
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
		val str1 = Dsl.starBoundText {
			pht("player_name")
			+"：这是一段"
			ct("blue") {
				ct("green") { +"彩" }
				+"色"
			}
			+"文本。"
		}
		//<player_name>：这是一段^blue;^green;彩^reset;色^reset;文本。
		println(str1)
		
		val str2 = Dsl.starBoundText {
			pht("player_name");+"：这是一段";ct("blue") { ct("green") { +"彩" };+"色" };+"文本。"
		}
		//<player_name>：这是一段^blue;^green;彩^reset;色^reset;文本。
		println(str2)
		
		val str3 = Dsl.starBoundText {
			+"信息 " + pht("player_name") + (+"：这是一段") + ct("blue") { ct("green") { +"彩" } + (+"色") } + (+"文本。")
		}
		//信息 <player_name>：这是一段^blue;^green;彩^reset;色^reset;文本。
		println(str3)
		
		val str4 = Dsl.starBoundText {
			+"信息 " + pht("player_name") + "：这是一段" + ct("blue") { ct("green") { +"彩" } + "色" } + "文本。"
		}
		//信息 <player_name>：这是一段^blue;^green;彩^reset;色^reset;文本。
		println(str4)
		
		//NOTE 不能直接使用模版字符串。因为难以提取单独的文本
		val str5 = Dsl.starBoundText {
			"信息 ${pht("player_name")}：这是一段${ct("blue") { ct("green") { +"彩" } + "色" }}文本。"
		}
		//信息 <player_name>：这是一段^blue;^green;彩^reset;色^reset;文本。
		println(str5)
	}
}
