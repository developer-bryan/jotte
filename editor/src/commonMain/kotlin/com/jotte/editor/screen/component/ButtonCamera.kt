package com.jotte.editor.screen.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jotte.cxui.Res
import com.jotte.cxui.component.CXButtonIcon
import com.jotte.cxui.icon_camera
import com.jotte.cxui.theme.colors

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