package com.jotte.cxui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalViewConfiguration
import androidx.compose.ui.unit.Density
import com.jotte.cxui.color.CXColors
import com.jotte.cxui.color.CXDarkColors
import com.jotte.cxui.color.CXLightColors
import com.jotte.cxui.composition.LocalColor
import com.jotte.cxui.composition.LocalSize
import com.jotte.cxui.composition.LocalSystemTheme
import com.jotte.cxui.composition.LocalTypography
import com.jotte.cxui.composition.SystemTheme
import com.jotte.cxui.extension.getCustomViewConfig
import com.jotte.cxui.shape.CXShape
import com.jotte.cxui.size.CXIconSize
import com.jotte.cxui.size.CXSize
import com.jotte.cxui.typography.CXTypography
import com.jotte.cxui.typography.createTypographyAttributes

val colors: CXColors
    @Composable get() = LocalColor.current ?: CXLightColors()

val sizes: CXSize
    @Composable get() = LocalSize.current

val iconSizes: CXIconSize by lazy { CXIconSize() }

val typography: CXTypography
    @Composable get() = LocalTypography.current

val shapes: CXShape by lazy { CXShape() }

val density: Density
    @Composable get() = LocalDensity.current

@Composable
fun CXTheme(
    isDarkMode: Boolean = isSystemInDarkTheme(),
    content: @Composable BoxWithConstraintsScope.() -> Unit
) {

    val systemTheme = if (isDarkMode) SystemTheme.DARK else SystemTheme.LIGHT
    val colors = if (isDarkMode) CXDarkColors() else CXLightColors()
    val size = CXSize()
    val typography = createTypographyAttributes()

    val textSelectionColors = remember {
        TextSelectionColors(
            handleColor = colors.accentColor,
            backgroundColor = colors.accentColor.copy(alpha = 0.4F)
        )
    }

    val localViewConfig = LocalViewConfiguration.current

    val customViewConfiguration = remember {
        localViewConfig.getCustomViewConfig(longPressTimeoutMillis = 150L)
    }

    CompositionLocalProvider(
        LocalColor provides colors,
        LocalSize provides size,
        LocalTypography provides typography,
        LocalSystemTheme provides systemTheme,
        LocalTextSelectionColors provides textSelectionColors,
        LocalViewConfiguration provides customViewConfiguration
    ) {

        BoxWithConstraints(
            modifier = Modifier.fillMaxSize(),
            content = content
        )
    }
}
