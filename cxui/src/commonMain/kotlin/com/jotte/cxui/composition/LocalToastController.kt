package com.jotte.cxui.composition

import androidx.compose.runtime.staticCompositionLocalOf
import com.jotte.cxui.controller.CXToastController

val LocalToastController = staticCompositionLocalOf<CXToastController> { error("toast state not set") }