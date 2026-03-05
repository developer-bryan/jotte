package com.jotte.camera.model

import androidx.compose.ui.geometry.Offset

data class CameraState(
    val zoom: Float = 1f,
    val focusOffset: Offset? = null,
    val flashOn: Boolean = false,
    val showGridLines: Boolean = false,
    val rearCameraZOrder: Float = 0F,
    val selfieCameraZOrder: Float = -1F,
    val isRearCameraInFocus: Boolean = rearCameraZOrder > selfieCameraZOrder,
)
