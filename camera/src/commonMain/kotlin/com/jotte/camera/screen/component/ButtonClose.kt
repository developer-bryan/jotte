package com.jotte.camera.screen.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jotte.cxui.Res
import com.jotte.cxui.component.CXButtonIcon
import com.jotte.cxui.icon_close

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