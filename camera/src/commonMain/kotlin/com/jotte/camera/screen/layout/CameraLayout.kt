package com.jotte.camera.screen.layout

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jotte.core.VirtualFile

@Composable
internal expect fun CameraLayout(
    modifier: Modifier = Modifier,
    onCloseClicked: () -> Unit,
    onMediaCaptured: (file: VirtualFile) -> Unit
)