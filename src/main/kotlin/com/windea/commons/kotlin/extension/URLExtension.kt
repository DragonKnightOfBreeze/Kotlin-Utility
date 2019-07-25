package com.windea.commons.kotlin.extension

import java.net.*

/**是否存在查询参数。*/
val URL.hasQueryParam get() = this.query.isNotBlank()

/**查询参数映射。*/
val URL.queryParamMap get() = this.query.toQueryParamMap()
