@file:OptIn(ExperimentalTime::class)
package com.jotte.core.datetime.usecase

import com.jotte.core.datetime.DateTimeStrings
import kotlinx.datetime.DateTimeArithmeticException
import kotlinx.datetime.Instant
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@Deprecated("Use GetFullDateUseCase instead")
class GetDateUseCase(private val dateTimeStrings: DateTimeStrings) {

    operator fun invoke(timestamp: Long = Clock.System.now().toEpochMilliseconds()): String {

        return try {
            val instant = Instant.fromEpochMilliseconds(timestamp)
            val date = instant.toLocalDateTime(TimeZone.currentSystemDefault())

            val month = getMonthName(date.month)
            val day = getDayOrdinal(date.dayOfMonth)
            val year = date.year

            "$month $day, $year"
        } catch (e: DateTimeArithmeticException) { "" }
    }

    private fun getMonthName(month: Month): String = when (month) {
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

    private fun getDayOrdinal(day: Int): String {
        var ordinal = when(day) {
            1 -> dateTimeStrings.ordinals.st
            2 -> dateTimeStrings.ordinals.nd
            3 -> dateTimeStrings.ordinals.rd
            22 -> dateTimeStrings.ordinals.nd
            23 -> dateTimeStrings.ordinals.rd
            else -> dateTimeStrings.ordinals.th
        }
        return "$day$ordinal"
    }

}