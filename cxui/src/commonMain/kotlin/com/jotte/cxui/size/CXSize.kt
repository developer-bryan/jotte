package com.jotte.cxui.size

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class CXSize internal constructor(
    val extraTiny: Dp = 4.dp,
    val tiny: Dp = 6.dp,
    var extraSmall: Dp = 8.dp,
    var small: Dp = 12.dp,
    var regular: Dp = 16.dp,
    val medium: Dp = 22.dp,
    val large: Dp = 26.dp,
    val extraLarge: Dp = 32.dp,
    val huge: Dp = 62.dp,
    val interactableHeightSmall: Dp = 46.dp,
    val interactableHeight: Dp = 56.dp,
    val toolbarHeight: Dp = 62.dp,
    val footerHeight: Dp = 58.dp,
    val defaultScrollChoke: Float = 0.4f,
    val popupBottomPosition: Dp = footerHeight,
    val elevation: Dp = 8.dp,
    val roomDrawerWidth: Dp = 326.dp,
    val reducedAlpha: Float = 0.75F,
    val noteContentMaxWidthPercentage: Float = 0.95F,
    val aspectRatio43: Float = 0.75F
)
