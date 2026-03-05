package com.jottie.core.datetime

data class DateTimeStrings(
    val months: Months,
    val ordinals: Ordinals,
    val periods: Periods
) {

    data class Months(
        val january: String,
        val february: String,
        val march: String,
        val april: String,
        val may: String,
        val june: String,
        val july: String,
        val august: String,
        val september: String,
        val october: String,
        val november: String,
        val december: String
    )

    data class Ordinals(
        val st: String,
        val nd: String,
        val rd: String,
        val th: String
    )

    data class Periods(
        val am: String,
        val pm: String
    )
}
