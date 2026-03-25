package com.jotte.camera.model.intent

import androidx.camera.core.MeteringPointFactory
import androidx.camera.core.Preview
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.LifecycleOwner
import com.jotte.core.VirtualFile

sealed interface Intent {
    data object ToggleFlash : Intent
    data object StopCamera : Intent
    data object ToggleGridLines : Intent
    data class ToggleCamera(val lifecycleOwner: LifecycleOwner) : Intent
    data class CaptureImage(val callback: (VirtualFile) -> Unit) : Intent
    data class StartCamera(val lifecycleOwner: LifecycleOwner) : Intent
    data class SetZoom(val zoom: Float) : Intent
    data class Initialize(val owner: LifecycleOwner) : Intent
    data class SetFocusPoint(val offset: Offset) : Intent
    data class SetSurfaceProvider(
        val surface: Preview.SurfaceProvider,
        val meterFactory: MeteringPointFactory
    ) : Intent
}