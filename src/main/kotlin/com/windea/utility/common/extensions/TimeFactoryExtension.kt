package com.windea.utility.common.extensions

import java.time.*

/**创建指定纳秒的时长。*/
inline val Int.nanoseconds get() = Duration.ofNanos(this.toLong())!!

/**创建指定毫秒的时长。*/
inline val Int.milliseconds get() = Duration.ofMillis(this.toLong())!!

/**创建指定秒的时长。*/
inline val Int.seconds get() = Duration.ofSeconds(this.toLong())!!

/**创建指定分钟的时长。*/
inline val Int.minutes get() = Duration.ofMinutes(this.toLong())!!

/**创建指定小时的时长。*/
inline val Int.hours get() = Duration.ofHours(this.toLong())!!

/**创建指定天数的时长。*/
inline val Int.days get() = Duration.ofDays(this.toLong())!!


/**创建指定周数的时期。*/
inline val Int.weeks get() = Period.ofWeeks(this)!!

/**创建指定月数的时期。*/
inline val Int.months get() = Period.ofMonths(this)!!

/**创建指定年数的时期。*/
inline val Int.years get() = Period.ofYears(this)!!


/**创建指定纳秒的时长。*/
inline val Long.nanoseconds get() = Duration.ofNanos(this)!!

/**创建指定毫秒的时长。*/
inline val Long.milliseconds get() = Duration.ofMillis(this)!!

/**创建指定秒的时长。*/
inline val Long.seconds get() = Duration.ofSeconds(this)!!

/**创建指定分钟的时长。*/
inline val Long.minutes get() = Duration.ofMinutes(this)!!

/**创建指定小时的时长。*/
inline val Long.hours get() = Duration.ofHours(this)!!

/**创建指定天数的时长。*/
inline val Long.days get() = Duration.ofDays(this)!!
