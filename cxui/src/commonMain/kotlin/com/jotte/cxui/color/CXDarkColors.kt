package com.jotte.cxui.color

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
open class CXDarkColors: CXColorsShared() {
    override val backgroundPrimary: Color = Pallete.Black
    override val backgroundSecondary: Color = Pallete.MidnightBlue
    override val backgroundPrimaryInverse: Color = Pallete.White
    override val backgroundSecondaryInverse: Color = Pallete.ShyBlue
    override val contentPrimary: Color = Pallete.MilkWhite
    override val contentPrimaryReducedAlpha: Color = Pallete.MilkWhite.copy(alpha = 0.75F)
    override val contentPrimaryLowAlpha: Color = Pallete.MilkWhite.copy(alpha = 0.50F)
    override val contentPrimaryInverse: Color = Pallete.LightBlack
}