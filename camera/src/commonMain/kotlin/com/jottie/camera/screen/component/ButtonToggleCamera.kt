package com.jottie.camera.screen.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jottie.cxui.Res
import com.jottie.cxui.component.CXButtonIcon
import com.jottie.cxui.icon_flip
import com.jottie.cxui.theme.iconSizes

@Composable
internal fun ButtonToggleCamera(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    CXButtonIcon(
        icon = Res.drawable.icon_flip,
        iconSize = iconSizes.medium,
        modifier = modifier,
        onClick = onClick
    )

}