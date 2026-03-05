package com.jotte.cxui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.jotte.cxui.composition.LocalSystemTheme
import com.jotte.cxui.composition.SystemTheme

/**
 * ThemedBox is a platform styling container that is implemented per-screen which
 * allows its child to determine its own window insets, window color, system decor color, etc.
 * This approach is more flexible than having the Box(...) in Theme.kt manage this for EVERY screen.
 *
 * The trade-off being each screen needs to wrap themselves in this composable.
 *
 * TL:DR this composable allows a screen to modify the `window` background
 *       THEN apply the window insets.
 */
@Composable
fun CXThemeBox(
    modifier: Modifier = Modifier,
    windowBackground: Color = colors.backgroundPrimary,
    windowBrush: Brush? = null,
    useLightSystemDecor: Boolean = LocalSystemTheme.current == SystemTheme.DARK,
    windowInsets: WindowInsets = WindowInsets.systemBars,
    content: @Composable BoxScope.() -> Unit
) {

    ToggleSystemDecorTheme(useLightSystemDecor)

    val backgroundModifier = if (windowBrush != null) {
        Modifier.background(windowBrush)
    } else {
        Modifier.background(windowBackground)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .then(backgroundModifier)
            .windowInsetsPadding(windowInsets)
            .then(modifier),
        content = content
    )
}