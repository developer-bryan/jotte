package com.jottie.camera.screen.component

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import com.jottie.camera.model.Zoomable

@Composable
internal fun CameraGestureComponent(
    modifier: Modifier = Modifier,
    zoomable: Zoomable,
    onTap: (offset: Offset) -> Unit,
    onZoomChange: (zoom: Float) -> Unit
) {

    Box(
        modifier = modifier
            .fillMaxSize()
            .pointerInput("tap") { detectTapGestures(onTap = onTap) }
            .pointerInput("zoom") {
                detectTransformGestures { _, _, delta, _ ->
                    val currentZoom = zoomable.getZoom()
                    val range = zoomable.getZoomRange()
                    val zoom = (currentZoom * delta).coerceIn(range)
                    onZoomChange(zoom)
                }
            }
    )
}