package com.jotte.cxui.composition

import androidx.compose.runtime.staticCompositionLocalOf
import com.jotte.cxui.controller.CXClipboardController

val LocalClipboardController = staticCompositionLocalOf<CXClipboardController> { error("missing clipboard state") }