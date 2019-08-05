package com.windea.utility.common.extensions

import java.time.*

operator fun Duration.unaryMinus() = this.negated()!!
operator fun Duration.times(n: Long) = this.multipliedBy(n)!!
operator fun Duration.div(n: Long) = this.dividedBy(n)!!
operator fun Duration.component1() = this.seconds
operator fun Duration.component2() = this.nano

operator fun Period.unaryMinus() = this.negated()!!
operator fun Period.times(n: Int) = this.multipliedBy(n)!!
operator fun Period.component1() = this.years
operator fun Period.component2() = this.months
operator fun Period.component3() = this.days

operator fun Year.plus(years: Int) = this.plusYears(years.toLong())!!
operator fun Year.minus(years: Int) = this.minusYears(years.toLong())!!
operator fun Year.inc() = this.plusYears(1L)!!
operator fun Year.dec() = this.minusYears(1L)!!

operator fun Month.plus(months: Int) = this.plus(months.toLong())!!
operator fun Month.minus(months: Int) = this.minus(months.toLong())!!
operator fun Month.inc() = this.plus(1L)!!
operator fun Month.dec() = this.minus(1L)!!

operator fun DayOfWeek.plus(days: Int) = this.plus(days.toLong())!!
operator fun DayOfWeek.minus(days: Int) = this.minus(days.toLong())!!
operator fun DayOfWeek.inc(): DayOfWeek = this.plus(1L)
operator fun DayOfWeek.dec(): DayOfWeek = this.minus(1L)


/**是否是明天。*/
val LocalDate.isTomorrow
	get() = LocalDate.now().let { this.year == it.year && this.dayOfYear == it.dayOfYear + 1 }

/**是否是今天。*/
val LocalDate.isToday
	get() = LocalDate.now().let { this.year == it.year && this.dayOfYear == it.dayOfYear }

/**是否是昨天。*/
val LocalDate.isYesterday
	get() = LocalDate.now().let { this.year == it.year && this.dayOfYear == it.dayOfYear - 1 }


/**是否是未来。*/
val LocalDate.isInFuture get() = this > LocalDate.now()

/**是否是过去。*/
val LocalDate.isInPast get() = this < LocalDate.now()


/**是否是明天。*/
val LocalDateTime.isTomorrow
	get() = LocalDateTime.now().let { this.year == it.year && this.dayOfYear == it.dayOfYear + 1 }

/**是否是今天。*/
val LocalDateTime.isToday
	get() = LocalDateTime.now().let { this.year == it.year && this.dayOfYear == it.dayOfYear }

/**是否是昨天。*/
val LocalDateTime.isYesterday
	get() = LocalDateTime.now().let { this.year == it.year && this.dayOfYear == it.dayOfYear - 1 }


/**是否是未来。*/
val LocalDateTime.isInFuture get() = this > LocalDateTime.now()

/**是否是过去。*/
val LocalDateTime.isInPast get() = this < LocalDateTime.now()
