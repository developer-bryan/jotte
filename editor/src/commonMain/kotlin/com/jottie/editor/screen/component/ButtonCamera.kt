package com.jottie.editor.screen.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jottie.cxui.Res
import com.jottie.cxui.component.CXButtonIcon
import com.jottie.cxui.icon_camera
import com.jottie.cxui.theme.colors

@Composable
internal fun ButtonCamera(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    CXButtonIcon(
        modifier = modifier,
        icon = Res.drawable.icon_camera,
        backgroundColor = colors.contentPrimary,
        iconColor = colors.contentPrimaryInverse,
        onClick = onClick
    )
}