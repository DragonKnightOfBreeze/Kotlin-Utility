package com.windea.utility.common.domain.collections

interface Tree<T> : Map<T, Tree<T>> {
	val rootNode: T
	
	val nodes: T
	
	val flatSize: Int
	
	fun isFlatEmpty(): Boolean
	
	operator fun get(nodePath: Array<T>): Tree<T>
	
	fun getNode(nodePath: Array<T>): Tree<T>
	
	fun getNodeOrDefault(nodePath: Array<T>, defaultValue: Tree<T>): Tree<T>
	
	fun containsNode(node: T): Boolean
}

interface MutableTree<T> : MutableMap<T, MutableTree<T>> {
	fun putNode(nodePath: Array<T>, value: MutableTree<T>): MutableTree<T>?
	
	fun putAllNode(from: Map<out Array<T>, MutableTree<T>>)
	
	fun putNodeIfAbsent(nodePath: Array<T>, value: MutableTree<T>): MutableTree<T>?
	
	fun removeNode(nodePath: Array<T>): MutableTree<T>?
	
	fun removeNode(nodePath: Array<T>, value: MutableTree<T>): MutableTree<T>?
}
