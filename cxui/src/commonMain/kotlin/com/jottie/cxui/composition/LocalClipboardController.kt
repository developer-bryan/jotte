package com.jottie.cxui.composition

import androidx.compose.runtime.staticCompositionLocalOf
import com.jottie.cxui.controller.CXClipboardController

val LocalClipboardController = staticCompositionLocalOf<CXClipboardController> { error("missing clipboard state") }