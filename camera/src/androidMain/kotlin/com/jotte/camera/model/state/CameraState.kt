package com.jotte.camera.model.state

import androidx.camera.core.CameraSelector
import androidx.compose.ui.geometry.Offset

internal data class CameraState(
    val position: @CameraSelector.LensFacing Int = CameraSelector.LENS_FACING_BACK,
    val focusOffset: Offset? = null,
    val hasFlashUnit: Boolean = true,
    val focusSupported: Boolean = true,
    val flashEnabled: Boolean = false,
    val showGridLines: Boolean = false
)