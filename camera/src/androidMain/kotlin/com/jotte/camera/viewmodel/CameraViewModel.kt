@file:OptIn(ExperimentalTime::class)

package com.jotte.camera.viewmodel

import android.app.Application
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.CameraSelector.LENS_FACING_BACK
import androidx.camera.core.CameraSelector.LENS_FACING_FRONT
import androidx.camera.core.FocusMeteringAction
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCapture.FLASH_MODE_OFF
import androidx.camera.core.ImageCapture.FLASH_MODE_ON
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.MeteringPointFactory
import androidx.camera.core.Preview
import androidx.camera.core.UseCase
import androidx.camera.core.UseCaseGroup
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import com.jotte.camera.model.Zoomable
import com.jotte.camera.model.intent.Intent
import com.jotte.camera.model.state.CameraState
import com.jotte.camera.usecase.GetCameraProcessProviderUseCase
import com.jotte.core.VirtualFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import java.lang.System.currentTimeMillis
import java.util.concurrent.TimeUnit
import kotlin.time.ExperimentalTime

internal class CameraViewModel(
    application: Application,
    private val getCameraProcessProviderUseCase: GetCameraProcessProviderUseCase,
    private val preview: Preview,
    private val imageCapture: ImageCapture
) : AndroidViewModel(application) {

    private var camera: Camera? = null
    private var process: ProcessCameraProvider? = null
    private lateinit var meteringPointFactory: MeteringPointFactory

    private val rear = CameraSelector.DEFAULT_BACK_CAMERA
    private val selfie = CameraSelector.DEFAULT_FRONT_CAMERA

    var currentZoom = MutableStateFlow(1F)
    val config = MutableStateFlow(CameraState())

    fun onIntent(intent: Intent) {
        when (intent) {
            Intent.ToggleFlash -> toggleFlash()
            Intent.StopCamera -> process?.unbindAll()
            Intent.ToggleGridLines -> config.update { it.copy(showGridLines = !it.showGridLines) }
            is Intent.StartCamera ->
                bindCamera(
                    intent.lifecycleOwner,
                    if (config.value.position == LENS_FACING_BACK) rear else selfie,
                    imageCapture,
                    preview,
                )

            is Intent.Initialize -> {
                viewModelScope.launch {
                    process = getCameraProcessProviderUseCase()
                    onIntent(Intent.StartCamera(intent.owner))
                }
            }

            is Intent.ToggleCamera -> toggleCamera(intent.lifecycleOwner)
            is Intent.SetFocusPoint -> setFocusPoint(intent.offset)
            is Intent.SetSurfaceProvider -> setupPreview(intent.surface, intent.meterFactory)
            is Intent.CaptureImage -> takePicture(intent.callback)
            is Intent.SetZoom -> setZoom(intent.zoom)
        }
    }

    private fun toggleCamera(owner: LifecycleOwner) {
        val currentPosition = config.value.position
        val newPosition =
            if (currentPosition == LENS_FACING_BACK) LENS_FACING_FRONT else LENS_FACING_BACK
        val newLens = if (newPosition == LENS_FACING_BACK) rear else selfie
        bindCamera(
            owner,
            newLens,
            preview,
            imageCapture,
        )

        config.update { it.copy(position = newPosition) }
    }

    private fun toggleFlash() {
        val cameraInfo = camera?.cameraInfo
        if (cameraInfo == null || !cameraInfo.hasFlashUnit()) {
            return
        }

        val currentFlash = config.value.flashEnabled
        val newFlashMode = if (currentFlash) FLASH_MODE_OFF else FLASH_MODE_ON

        imageCapture.flashMode = newFlashMode
        camera?.cameraControl?.enableTorch(!currentFlash)
        config.update { it.copy(flashEnabled = !currentFlash) }
    }

    @Suppress("MagicNumber")
    private fun setFocusPoint(offset: Offset) {
        val point = meteringPointFactory.createPoint(offset.x, offset.y)
        val action =
            FocusMeteringAction
                .Builder(point, FocusMeteringAction.FLAG_AF)
                .setAutoCancelDuration(3, TimeUnit.SECONDS)
                .build()

        camera?.cameraControl?.startFocusAndMetering(action)
        config.update { it.copy(focusOffset = offset) }
    }

    private fun setupPreview(
        surface: Preview.SurfaceProvider,
        meteringFactory: MeteringPointFactory
    ) {
        preview.surfaceProvider = surface
        meteringPointFactory = meteringFactory
    }

    private fun setZoom(zoom: Float) {
        currentZoom.tryEmit(zoom)
        camera?.cameraControl?.setZoomRatio(zoom)
    }

    private fun takePicture(callback: (file: VirtualFile) -> Unit) {
        val fileName = "camera-media-${currentTimeMillis()}.jpeg"
        val cacheFile = VirtualFile(fileName, true)
        val outputFile = File(cacheFile.asVirtualPath())

        imageCapture.takePicture(
            ImageCapture.OutputFileOptions.Builder(outputFile).build(),
            Dispatchers.Main.asExecutor(),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    callback(cacheFile)
                }

                override fun onError(exception: ImageCaptureException) {
                    // TODO: Handle Errors
                }
            }
        )
    }

    private fun bindCamera(
        owner: LifecycleOwner,
        lens: CameraSelector,
        vararg useCases: UseCase
    ) {

        process?.unbindAll()

        val useCaseGroup =
            UseCaseGroup
                .Builder()
                .apply { useCases.forEach { addUseCase(it) } }
                .build()

        camera = process?.bindToLifecycle(owner, lens, useCaseGroup)

        val hasFlashUnit = camera?.cameraInfo?.hasFlashUnit() == true

        setZoom(1F)
        imageCapture.flashMode = FLASH_MODE_OFF
        camera?.cameraControl?.enableTorch(false)

        config.update {
            it.copy(
                flashEnabled = false,
                hasFlashUnit = hasFlashUnit
            )
        }
    }

    internal inner class ZoomableProvider : Zoomable {
        override fun getZoom(): Float =
            camera
                ?.cameraInfo
                ?.zoomState
                ?.value
                ?.zoomRatio ?: 1f

        override fun getZoomRange(): ClosedRange<Float> =
            camera?.cameraInfo?.zoomState?.value?.let {
                (it.minZoomRatio..it.maxZoomRatio)
            } ?: (1f..1f)
    }

}