package com.jottie.camera.screen.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jottie.cxui.Res
import com.jottie.cxui.component.CXButtonIcon
import com.jottie.cxui.icon_close

@Composable
internal fun ButtonClose(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    CXButtonIcon(
        icon = Res.drawable.icon_close,
        modifier = modifier,
        onClick = onClick
    )

}