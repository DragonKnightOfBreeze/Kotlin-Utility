import com.windea.utility.common.extensions.*
import org.junit.*

class CollectionExtensionTests {
	@Test
	fun test1() {
		val list1 = mutableListOf(1, 2, 3, 4).also { it.removeAllAt(1..2) }
		println(list1)
		
		val list2 = mutableListOf(1, 2, 3, 4).also { it.move(1, 3) }
		println(list2)
		
		val list3 = mutableListOf(1, 2, 3, 4, 5, 6).also { it.moveAll(1..3, 5) }
		println(list3)
	}
}


