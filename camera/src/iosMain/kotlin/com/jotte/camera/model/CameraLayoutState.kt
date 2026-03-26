package com.jotte.camera.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import com.jotte.camera.di.rearDevice
import com.jotte.camera.di.selfieDevice
import com.jotte.camera.viewcontroller.CameraViewController
import com.jotte.core.VirtualFile
import io.github.vinceglb.filekit.utils.toByteArray
import io.github.vinceglb.filekit.write
import io.ktor.util.date.getTimeMillis
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.compose.koinInject
import platform.AVFoundation.AVCaptureDevice
import platform.AVFoundation.AVCaptureFlashModeOff
import platform.AVFoundation.AVCaptureFlashModeOn

internal class CameraLayoutState(
    val scope: CoroutineScope,
    val backViewController: CameraViewController,
    val frontViewController: CameraViewController,
    private val onMediaCaptured: (VirtualFile) -> Unit
) : Zoomable {

    var viewController by mutableStateOf(backViewController)

    var state by mutableStateOf(CameraState())

    override fun getZoom(): Float = viewController.zoomable.getZoom()

    override fun getZoomRange(): ClosedRange<Float> = viewController.zoomable.getZoomRange()

    fun setFocusOffset(
        screenOffset: Offset,
        normalizedOffset: Offset
    ) {
        state = state.copy(focusOffset = screenOffset)
        viewController.setFocus(normalizedOffset.x, normalizedOffset.y)
    }

    fun onZoomed(zoom: Float) {
        viewController.setZoom(zoom)
        state = state.copy(zoom = viewController.getDisplayZoom(zoom))
    }

    fun toggleCamera() {
        viewController.stopSession()

        var rearCameraZOrder: Float
        var selfieCameraZOrder: Float

        if (state.isRearCameraInFocus) {
            viewController = frontViewController
            rearCameraZOrder = -1F
            selfieCameraZOrder = 0F
        } else {
            viewController = backViewController
            rearCameraZOrder = 0F
            selfieCameraZOrder = -1F
        }

        viewController.startSession()

        state =
            state.copy(
                zoom = 1F,
                flashOn = false,
                focusOffset = null,
                rearCameraZOrder = rearCameraZOrder,
                selfieCameraZOrder = selfieCameraZOrder,
                isRearCameraInFocus = rearCameraZOrder > selfieCameraZOrder,
            )

        viewController.setZoom(1F, true)
    }

    fun toggleGridLines() {
        state = state.copy(showGridLines = !state.showGridLines)
    }

    fun toggleFlash() {
        val newFlash = if (!state.flashOn) AVCaptureFlashModeOn else AVCaptureFlashModeOff
        viewController.setFlashMode(newFlash)
        state = state.copy(flashOn = !state.flashOn)
    }

    fun takePicture() {
        viewController.captureImage {
            scope.launch {
                withContext(Dispatchers.IO) {
                    val cacheFile = VirtualFile("camera-media-${getTimeMillis()}.jpg", true)
                    val bytes = it.toByteArray()
                    cacheFile.asFile().write(bytes)

                    withContext(Dispatchers.Main) {
                        onMediaCaptured(cacheFile)
                    }
                }
            }
        }
    }

    fun startSession() {
        viewController.startSession()
    }

    fun stopSession() {
        viewController.stopSession()
    }

}

@Composable
internal fun rememberCameraLayoutState(onMediaCaptured: (VirtualFile) -> Unit): CameraLayoutState {
    val rearDevice = koinInject<AVCaptureDevice>(rearDevice())
    val selfieDevice = koinInject<AVCaptureDevice>(selfieDevice())
    val scope = rememberCoroutineScope()

    return remember {

        val backViewController = CameraViewController(device = rearDevice, isEagerInit = true)
        val frontViewController = CameraViewController(device = selfieDevice, isEagerInit = false)

        CameraLayoutState(
            scope,
            backViewController,
            frontViewController,
            onMediaCaptured
        )
    }
}