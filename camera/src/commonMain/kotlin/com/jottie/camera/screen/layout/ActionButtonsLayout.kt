package com.jottie.camera.screen.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.jottie.camera.screen.component.ButtonClose
import com.jottie.camera.screen.component.ButtonFlash
import com.jottie.camera.screen.component.ButtonGrid
import com.jottie.camera.screen.component.ButtonToggleCamera
import com.jottie.cxui.extension.RowExtension.FillSpace
import com.jottie.cxui.theme.sizes

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
