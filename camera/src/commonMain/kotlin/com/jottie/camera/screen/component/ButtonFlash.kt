package com.jottie.camera.screen.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jottie.cxui.Res
import com.jottie.cxui.component.CXButtonIcon
import com.jottie.cxui.icon_flash_off
import com.jottie.cxui.icon_flash_on

@Composable
internal fun ButtonFlash(
    flashOn: Boolean = false,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    val icon = if (flashOn) Res.drawable.icon_flash_on else Res.drawable.icon_flash_off

    CXButtonIcon(
        icon = icon,
        modifier = modifier,
        onClick = onClick
    )
}