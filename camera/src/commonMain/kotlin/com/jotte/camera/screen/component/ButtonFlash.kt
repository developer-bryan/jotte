package com.jotte.camera.screen.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jotte.cxui.Res
import com.jotte.cxui.component.CXButtonIcon
import com.jotte.cxui.icon_flash_off
import com.jotte.cxui.icon_flash_on

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