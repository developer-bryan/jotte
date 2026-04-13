package com.jotte.cxui.shape

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

class CXShape {
    val flatShape = RoundedCornerShape(0.dp)
    val roundedSheetShape = RoundedCornerShape(20.dp)
    val roundedButtonShape = CircleShape
    val roundedInputFormShape = RoundedCornerShape(28.dp)
    val alertDialogShape = RoundedCornerShape(20.dp)
    val mediaPreviewShape = RoundedCornerShape(10.dp)
    val mediaPreviewHeadShape =
        RoundedCornerShape(
            topStart = 10.dp,
            bottomStart = 10.dp
        )
    val mediaPreviewTailShape =
        RoundedCornerShape(
            topEnd = 10.dp,
            bottomEnd = 10.dp
        )
    val toastShape = RoundedCornerShape(8.dp)
    val roundedSettingsValueButtonShape = RoundedCornerShape(12.dp)
    val roundedContentFormatterTrayShape = RoundedCornerShape(10.dp)
}