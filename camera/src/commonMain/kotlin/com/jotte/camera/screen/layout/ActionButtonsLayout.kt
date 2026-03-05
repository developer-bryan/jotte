package com.jotte.camera.screen.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.jotte.camera.screen.component.ButtonClose
import com.jotte.camera.screen.component.ButtonFlash
import com.jotte.camera.screen.component.ButtonGrid
import com.jotte.camera.screen.component.ButtonToggleCamera
import com.jotte.cxui.extension.RowExtension.FillSpace
import com.jotte.cxui.theme.sizes

@Composable
internal fun ActionButtonsLayout(
    modifier: Modifier = Modifier,
    isFlashToggled: Boolean = false,
    onCloseClicked: () -> Unit,
    onGridLinesClicked: () -> Unit,
    onFlashClicked: () -> Unit,
    onToggleCameraClicked: () -> Unit
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(sizes.small),
        content = {
            ButtonClose(onClick = onCloseClicked)
            FillSpace()
            ButtonFlash(flashOn = isFlashToggled, onClick = onFlashClicked)
            ButtonToggleCamera(onClick = onToggleCameraClicked)
            ButtonGrid(onClick = onGridLinesClicked)
        }
    )

}
