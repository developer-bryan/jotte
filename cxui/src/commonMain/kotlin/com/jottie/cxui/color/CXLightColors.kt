package com.jottie.cxui.color

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
open class CXLightColors: CXColorsShared() {
    override val backgroundPrimary: Color = Pallete.White
    override val backgroundSecondary: Color = Pallete.ShyBlue
    override val backgroundPrimaryInverse: Color = Pallete.Black
    override val backgroundSecondaryInverse: Color = Pallete.MidnightBlue
    override val contentPrimary: Color = Pallete.LightBlack
    override val contentPrimaryReducedAlpha: Color = Pallete.LightBlack.copy(alpha = 0.75F)
    override val contentPrimaryLowAlpha: Color = Pallete.LightBlack.copy(alpha = 0.10F)
    override val contentPrimaryInverse: Color = Pallete.MilkWhite
}