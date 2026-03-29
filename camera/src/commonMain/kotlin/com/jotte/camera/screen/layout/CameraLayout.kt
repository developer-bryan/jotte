package com.jotte.camera.screen.layout

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.vinceglb.filekit.PlatformFile

@Composable
internal expect fun CameraLayout(
    modifier: Modifier = Modifier,
    onCloseClicked: () -> Unit,
    onMediaCaptured: (file: PlatformFile) -> Unit
)