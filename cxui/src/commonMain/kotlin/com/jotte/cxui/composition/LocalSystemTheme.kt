package com.jotte.cxui.composition

import androidx.compose.runtime.compositionLocalOf

enum class SystemTheme {
    DARK, LIGHT, UNKOWN
}

fun SystemTheme.isDark() = this == SystemTheme.DARK

val LocalSystemTheme = compositionLocalOf { SystemTheme.UNKOWN }