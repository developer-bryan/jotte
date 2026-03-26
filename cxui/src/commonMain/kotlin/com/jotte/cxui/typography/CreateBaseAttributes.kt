package com.jotte.cxui.typography

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign

internal fun CXTypographySpecs.createTypography(): CXTypography =
    CXTypography(
        specs = this,
        headerOne =
            TextStyle(
                fontSize = this.fontSizeLarge,
                fontFamily = this.blackFont,
                lineHeight = this.headerLineHeight,
            ),
        headerTwo =
            TextStyle(
                fontSize = this.fontSizeMedium,
                fontFamily = this.blackFont,
                lineHeight = this.headerLineHeight,
            ),
        headerThree =
            TextStyle(
                fontSize = this.fontSizeMedium,
                fontFamily = this.blackFont,
                lineHeight = this.headerLineHeight,
            ),
        headerFour =
            TextStyle(
                fontSize = this.fontSizeSmall,
                fontFamily = this.blackFont,
                lineHeight = this.paragraphLineHeight,
            ),
        bodyOne =
            TextStyle(
                fontSize = this.fontSizeRegular,
                fontFamily = this.mediumFont,
                lineHeight = this.paragraphLineHeight,
            ),
        bodyTwo =
            TextStyle(
                fontSize = this.fontSizeSmall,
                fontFamily = this.normalFont,
                lineHeight = this.paragraphLineHeight,
            ),
        disclaimerText =
            TextStyle(
                fontSize = this.fontSizeRegular,
                fontFamily = this.mediumFont,
                textAlign = TextAlign.Center,
                lineHeight = this.paragraphLineHeight,
            ),
        link =
            TextStyle(
                fontSize = this.fontSizeRegular,
                fontFamily = this.mediumFont,
                textAlign = TextAlign.Center,
                lineHeight = this.paragraphLineHeight,
            ),
    )