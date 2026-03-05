package com.jotte.cxui.composition

import androidx.compose.runtime.staticCompositionLocalOf
import com.jotte.cxui.typography.CXTypography

val LocalTypography = staticCompositionLocalOf<CXTypography> { error("") }
