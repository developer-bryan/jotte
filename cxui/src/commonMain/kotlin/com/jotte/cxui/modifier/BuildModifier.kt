package com.jotte.cxui.modifier

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
inline fun Modifier.buildModifier(crossinline builder: @Composable Modifier.() -> Modifier): Modifier =
    builder(this)