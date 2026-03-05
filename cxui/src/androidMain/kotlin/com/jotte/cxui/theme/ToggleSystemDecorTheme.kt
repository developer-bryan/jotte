package com.jotte.cxui.theme

import android.graphics.Color
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.LocalActivity
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect

@Composable
actual fun ToggleSystemDecorTheme(useLightDecor: Boolean) {
    val activity = (LocalActivity.current as ComponentActivity)

    SideEffect {
        activity.enableEdgeToEdge(
            statusBarStyle = getSystemBarStyle(useLightDecor),
            navigationBarStyle = getSystemBarStyle(useLightDecor)
        )
    }
}

private fun getSystemBarStyle(useLightDecor: Boolean): SystemBarStyle {
    return if (useLightDecor) {
        SystemBarStyle.dark(Color.TRANSPARENT)
    } else {
        SystemBarStyle.light(
            scrim = Color.TRANSPARENT,
            darkScrim = Color.TRANSPARENT
        )
    }
}