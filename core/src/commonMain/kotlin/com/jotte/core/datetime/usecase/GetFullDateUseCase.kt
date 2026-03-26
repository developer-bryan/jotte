@file:Suppress("MagicNumber")

package com.jotte.core.datetime.usecase

import com.jotte.core.datetime.DateTimeStrings
import com.jotte.core.datetime.toDate
import kotlinx.datetime.Month

data class FullDate(
    val date: String,
    val time: String
)

class GetFullDateUseCase(private val dateTimeStrings: DateTimeStrings) {

    operator fun invoke(timestamp: Long): FullDate {
        val datetime = timestamp.toDate()

        return with(datetime) {
            val month = getMonthName(month)
            val day = getDayWithOrdinal(day)
            val year = year
            val hour = getHourAs12Hour(hour)
            val minute = minute.toString().padStart(2, '0')
            val period = getHourPeriod(this@with.hour)

            FullDate(
                date = "$month $day, $year",
                time = "$hour:$minute $period"
            )
        }

    }

    private fun getMonthName(month: Month): String =
        when (month) {
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

    private fun getDayWithOrdinal(day: Int): String {
        var ordinal =
            when (day) {
                1 -> dateTimeStrings.ordinals.st
                2 -> dateTimeStrings.ordinals.nd
                3 -> dateTimeStrings.ordinals.rd
                22 -> dateTimeStrings.ordinals.nd
                23 -> dateTimeStrings.ordinals.rd
                else -> dateTimeStrings.ordinals.th
            }
        return "$day$ordinal"
    }

    private fun getHourAs12Hour(hour: Int): Int =
        if (hour > 12) {
            hour - 12
        } else {
            hour
        }

    private fun getHourPeriod(hour: Int): String =
        if (hour >= 12) dateTimeStrings.periods.pm else dateTimeStrings.periods.am

}