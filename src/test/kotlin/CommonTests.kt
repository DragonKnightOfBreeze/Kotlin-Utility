import org.junit.*

class CommonTests {
	@Test
	fun test1() {
		println(1.0.toBigDecimal() - 0.9.toBigDecimal() == 0.1.toBigDecimal())
	}
}
