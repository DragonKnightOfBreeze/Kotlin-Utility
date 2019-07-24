package com.windea.commons.kotlin.enums.normal

/**方向。*/
interface Direction {
	/**包含所能到达的维度。*/
	val dimension: Array<Dimension>
	
	/**所能到达的最顶层维度。*/
	val topDimension: Dimension
}
