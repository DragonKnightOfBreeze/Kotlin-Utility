package com.windea.utility.common.generators.text

/**文本生成器的接口。*/
interface TextGenerator


typealias SchemaDefinitionMap = Map<String, SchemaMap>
typealias SchemaMap = Map<String, Map<String, Any>>
typealias SchemaRule = (Pair<String, Any?>) -> Map<String, Any?>

typealias SqlDataMap = Map<String, Map<String, List<Map<String, Any?>>>>
