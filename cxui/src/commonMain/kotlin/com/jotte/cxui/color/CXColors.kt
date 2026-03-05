package com.jotte.cxui.color

import androidx.compose.ui.graphics.Color

interface CXColors {
    val backgroundPrimary: Color // used for backgrounds
    val backgroundSecondary: Color // used for layer on top of backgrounds
    val backgroundPrimaryInverse: Color // inverse of backgroundPrimary
    val backgroundSecondaryInverse: Color // inverse of backgroundSecondary
    val contentPrimary: Color // used for content on backgroundPrimary
    val contentPrimaryReducedAlpha: Color // contentPrimary with 75% alpha reduction
    val contentPrimaryLowAlpha: Color // contentPrimary with 75% alpha reduction
    val contentPrimaryInverse: Color // inverse of content primary
    var accentColor: Color
    var onAccentColor: Color
    var accentReducedAlpha: Color

    val negativeColor: Color // used for negative action tints
    val scrim: Color
    val linkColor: Color
}
