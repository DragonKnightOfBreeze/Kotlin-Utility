# 概述

Kotlin实用代码整理。

# 说明

* 提供多种多样的扩展方法和属性。扩展了字符串、集合、时间以及随机数等。
* 提供一些字符串和文本的生成器。后续会提供更多泛用的生成器。
* 提供多种数据类型的读取器的封装实现。可以以字符串/文件的形式写入/输出。
* 提供一些常见的数据类、枚举和注解。不过有些可能就非常稀奇古怪了。
* 提供一些常见的和不常见的Dsl（领域专用语言）。

# 规定

* 除非有必要使用多行文档注释，否则一律使用单行文档注释。
* 除非包含可选参数，或者与某一项强相关，否则不要在注释中引用项。
* 除非是可以明确认为是重载/转化/工厂方法，或是非常简短的仅有一行表达式的方法，否则不要使用表达式写法。
* 尽量明确声明公共方法和属性的返回类型。
* 如果是扩展方法/属性，尽量显示加上`this`关键字。
