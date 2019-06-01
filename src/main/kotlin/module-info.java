module Kotlin.Utility {
	requires kotlin.stdlib;
	requires snakeyaml;
	requires gson;
	requires commons.logging;
	//为了解决snakeYaml报错ClassDefNotFoundException，必须添加这个依赖
	requires java.sql;
}
