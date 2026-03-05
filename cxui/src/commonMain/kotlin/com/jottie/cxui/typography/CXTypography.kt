package com.jottie.cxui.typography

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily

@ConsistentCopyVisibility
data class CXTypography internal constructor(
    val specs: CXTypographySpecs,
    val headerOne: TextStyle = TextStyle(),
    val headerTwo: TextStyle = TextStyle(),
    val headerThree: TextStyle = TextStyle(),
    val headerFour: TextStyle = TextStyle(),
    val bodyOne: TextStyle = TextStyle(),
    val bodyTwo: TextStyle = TextStyle(),
    val link: TextStyle = TextStyle(),
    val disclaimerText: TextStyle = TextStyle()
)

@Composable
internal fun createTypographyAttributes(): CXTypography {
    return CXTypographySpecs(
        normalFont = FontFamily(CXFont.Normal),
        mediumFont = FontFamily(CXFont.Medium),
        semiBoldFont = FontFamily(CXFont.SemiBold),
        blackFont = FontFamily(CXFont.Black),
    ).createTypography()
}