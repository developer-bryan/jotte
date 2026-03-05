package com.jottie.cxui.color

import androidx.compose.ui.graphics.Color

abstract class CXColorsShared: CXColors {
    override var accentColor: Color = Pallete.GrapeSoda
    override var onAccentColor: Color = Pallete.MilkWhite
    override var accentReducedAlpha: Color = Pallete.GrapeSoda.copy(alpha = 0.25F)
    override val negativeColor: Color = Pallete.Fuego
    override val linkColor: Color = Pallete.SkyBlue
    override val scrim: Color = Pallete.Scrim
}