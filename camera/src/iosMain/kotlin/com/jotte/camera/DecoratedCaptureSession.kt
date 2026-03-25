package com.jotte.camera

import kotlinx.cinterop.ExperimentalForeignApi
import platform.AVFoundation.AVCaptureDevice
import platform.AVFoundation.AVCaptureDeviceInput
import platform.AVFoundation.AVCaptureDevicePositionBack
import platform.AVFoundation.AVCaptureExposureModeContinuousAutoExposure
import platform.AVFoundation.AVCaptureFlashModeOff
import platform.AVFoundation.AVCaptureFocusModeAutoFocus
import platform.AVFoundation.AVCaptureOutput
import platform.AVFoundation.AVCaptureSession
import platform.AVFoundation.displayVideoZoomFactorMultiplier
import platform.AVFoundation.exposureMode
import platform.AVFoundation.exposurePointOfInterest
import platform.AVFoundation.exposurePointOfInterestSupported
import platform.AVFoundation.flashMode
import platform.AVFoundation.focusMode
import platform.AVFoundation.focusPointOfInterest
import platform.AVFoundation.focusPointOfInterestSupported
import platform.AVFoundation.isExposureModeSupported
import platform.AVFoundation.isFocusModeSupported
import platform.AVFoundation.position
import platform.AVFoundation.videoZoomFactor
import platform.CoreGraphics.CGPointMake

internal class DecoratedCaptureSession: AVCaptureSession() {

    @OptIn(ExperimentalForeignApi::class)
    internal inline fun runConfigurationChange(block: (device: AVCaptureDevice) -> Unit) {
        val device = getCurrentDevice()
        device?.lockForConfiguration(null)
        if (device != null) block(device)
        device?.unlockForConfiguration()
    }

    internal fun isStopped() = !isRunning()

    @OptIn(ExperimentalForeignApi::class)
    internal fun addInputDevice(device: AVCaptureDevice): Boolean {
        val input = AVCaptureDeviceInput(device, null)
        return addInputExt(input)
    }

    internal fun addInputExt(input: AVCaptureDeviceInput): Boolean {
        if (!canAddInput(input)) return false
        addInput(input)
        return true
    }

    internal fun addOutputExt(output: AVCaptureOutput) {
        if (canAddOutput(output)) addOutput(output)
    }

    @OptIn(ExperimentalForeignApi::class)
    internal fun setFocusPoint(x: Double, y: Double) = getCurrentDevice()?.let {
        it.focusPointOfInterest = CGPointMake(x, y)
        it.focusMode = AVCaptureFocusModeAutoFocus
    }

    @OptIn(ExperimentalForeignApi::class)
    internal fun setExposurePoint(x: Double, y: Double) = getCurrentDevice()?.let {
        it.exposurePointOfInterest = CGPointMake(x, y)
        it.exposureMode = AVCaptureExposureModeContinuousAutoExposure
    }

    internal fun getCurrentDevice() = (this.inputs.firstOrNull() as? AVCaptureDeviceInput)?.device

    internal fun getCurrentFlash() =
        getCurrentDevice()?.flashMode ?: AVCaptureFlashModeOff

    internal fun getCurrentPosition() =
        getCurrentDevice()?.position ?: AVCaptureDevicePositionBack

    internal fun displayZoomFactor() =
        getCurrentDevice()?.displayVideoZoomFactorMultiplier ?: 1.0

    internal fun currentZoom() = getCurrentDevice()?.videoZoomFactor

    internal fun canFocus() = getCurrentDevice()?.let {
        it.focusPointOfInterestSupported && it.isFocusModeSupported(AVCaptureFocusModeAutoFocus)
    } == true

    internal fun canSetExposure() = getCurrentDevice()?.let {
        it.exposurePointOfInterestSupported && it.isExposureModeSupported(
            AVCaptureExposureModeContinuousAutoExposure
        )
    } == true

}