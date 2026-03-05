package com.jottie.audioplayer.screen.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.jottie.cxui.component.CXText
import com.jottie.cxui.theme.typography

@Composable
internal fun AudioTitle(
    modifier: Modifier = Modifier,
    title: String? = null
) {

    CXText(
        text = title ?: "Audio Note",
        modifier = modifier,
        style = typography.headerOne.copy(fontSize = 32.sp),
        textAlign = TextAlign.Center
    )

}