package com.jottie.cxui.theme

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
import com.jottie.cxui.color.CXColors
import com.jottie.cxui.color.CXDarkColors
import com.jottie.cxui.color.CXLightColors
import com.jottie.cxui.composition.LocalColor
import com.jottie.cxui.composition.LocalSize
import com.jottie.cxui.composition.LocalSystemTheme
import com.jottie.cxui.composition.LocalTypography
import com.jottie.cxui.composition.SystemTheme
import com.jottie.cxui.extension.getCustomViewConfig
import com.jottie.cxui.shape.CXShape
import com.jottie.cxui.size.CXIconSize
import com.jottie.cxui.size.CXSize
import com.jottie.cxui.typography.CXTypography
import com.jottie.cxui.typography.createTypographyAttributes

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
