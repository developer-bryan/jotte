package com.jotte.core.datetime.usecase

import com.jotte.core.datetime.DateTimeStrings
import com.jotte.core.datetime.getMonthFrom
import com.jotte.core.datetime.getYearFrom
import kotlinx.datetime.Month

class GetMonthYearUseCase(private val dateTimeStrings: DateTimeStrings) {

    operator fun invoke(timestamp: Long): String {
        val month =
            when (getMonthFrom(timestamp)) {
                Month.JANUARY -> dateTimeStrings.months.january
                Month.FEBRUARY -> dateTimeStrings.months.february
                Month.MARCH -> dateTimeStrings.months.march
                Month.APRIL -> dateTimeStrings.months.april
                Month.MAY -> dateTimeStrings.months.may
                Month.JUNE -> dateTimeStrings.months.june
                Month.JULY -> dateTimeStrings.months.july
                Month.AUGUST -> dateTimeStrings.months.august
                Month.SEPTEMBER -> dateTimeStrings.months.september
                Month.OCTOBER -> dateTimeStrings.months.october
                Month.NOVEMBER -> dateTimeStrings.months.november
                Month.DECEMBER -> dateTimeStrings.months.december
            }

        val year = getYearFrom(timestamp)

        return "$month, $year"
    }

}
