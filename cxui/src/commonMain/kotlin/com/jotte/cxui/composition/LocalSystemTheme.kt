package com.jotte.cxui.composition

import androidx.compose.runtime.compositionLocalOf

val LocalSystemTheme = compositionLocalOf { SystemTheme.UNKNOWN }

enum class SystemTheme { DARK, LIGHT, UNKNOWN }
