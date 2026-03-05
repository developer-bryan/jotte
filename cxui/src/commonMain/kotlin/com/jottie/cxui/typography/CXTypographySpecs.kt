package com.jottie.cxui.typography

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

data class CXTypographySpecs(
    val normalFont: FontFamily,
    val mediumFont: FontFamily,
    val semiBoldFont: FontFamily,
    val blackFont: FontFamily,
    val fontSizeSmall: TextUnit = 12.sp,
    val fontSizeRegular: TextUnit = 14.sp,
    val fontSizeMedium: TextUnit = 16.sp,
    val fontSizeLarge: TextUnit = 22.sp,
    val paragraphLineHeight: TextUnit = 26.sp,
    val headerLineHeight: TextUnit = 28.sp,
)