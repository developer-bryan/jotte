package com.jottie.cxui.composition

import androidx.compose.runtime.staticCompositionLocalOf
import com.jottie.cxui.controller.CXToastController

val LocalToastController = staticCompositionLocalOf<CXToastController> { error("toast state not set") }