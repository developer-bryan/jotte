@file:OptIn(ExperimentalComposeUiApi::class)
@file:Suppress("FunctionNaming", "ktlint:all")

package com.jotte.app

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeUIViewController

fun MainViewController() = ComposeUIViewController(
    configure = { parallelRendering = true },
    content = { App() }
)