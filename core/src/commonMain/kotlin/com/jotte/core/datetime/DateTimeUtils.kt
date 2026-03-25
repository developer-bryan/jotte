@file:OptIn(ExperimentalTime::class)
package com.jotte.core.datetime

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.toDuration

private const val ZERO_PADDING_THRESHOLD = 10

fun Long.toDate(timezone: TimeZone = TimeZone.currentSystemDefault()): LocalDateTime =
    Instant.fromEpochMilliseconds(this).toLocalDateTime(timezone)

fun getMonthFrom(timestamp: Long): Month = timestamp.toDate().month
fun getYearFrom(timestamp: Long): Int = timestamp.toDate().year

fun Long.toFormattedRuntime(): String {
    val minuteMillis = 1.toDuration(DurationUnit.MINUTES).inWholeMilliseconds
    val secondMillis = 1.toDuration(DurationUnit.SECONDS).inWholeMilliseconds

    val minutes = this / minuteMillis
    val seconds = (this % minuteMillis) / secondMillis

    val secondsDisplayString = if (seconds < ZERO_PADDING_THRESHOLD) "0$seconds" else "$seconds"

    return "$minutes:$secondsDisplayString"
}