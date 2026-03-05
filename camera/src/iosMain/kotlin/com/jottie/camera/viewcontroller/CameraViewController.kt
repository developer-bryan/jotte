@file:OptIn(ExperimentalForeignApi::class, BetaInteropApi::class, ExperimentalTime::class)

package com.jottie.camera.viewcontroller

import co.touchlab.kermit.Logger
import com.jottie.camera.DecoratedCaptureSession
import com.jottie.camera.model.Zoomable
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCAction
import platform.AVFoundation.AVCaptureDevice
import platform.AVFoundation.AVCaptureDevicePositionFront
import platform.AVFoundation.AVCaptureFlashMode
import platform.AVFoundation.AVCapturePhoto
import platform.AVFoundation.AVCapturePhotoCaptureDelegateProtocol
import platform.AVFoundation.AVCapturePhotoOutput
import platform.AVFoundation.AVCapturePhotoSettings
import platform.AVFoundation.AVCaptureSession
import platform.AVFoundation.AVCaptureSessionDidStartRunningNotification
import platform.AVFoundation.AVCaptureSessionDidStopRunningNotification
import platform.AVFoundation.AVCaptureSessionPresetPhoto
import platform.AVFoundation.AVCaptureVideoPreviewLayer
import platform.AVFoundation.AVLayerVideoGravityResizeAspectFill
import platform.AVFoundation.AVMediaTypeVideo
import platform.AVFoundation.fileDataRepresentation
import platform.AVFoundation.flashMode
import platform.AVFoundation.hasFlash
import platform.AVFoundation.videoZoomFactor
import platform.Foundation.NSData
import platform.Foundation.NSError
import platform.Foundation.NSNotification
import platform.Foundation.NSNotificationCenter
import platform.Foundation.NSSelectorFromString
import platform.UIKit.UIColor
import platform.UIKit.UIViewController
import platform.darwin.NSObject
import platform.darwin.dispatch_async as runAsync
import platform.darwin.dispatch_get_global_queue as backgroundQueue
import platform.darwin.dispatch_get_main_queue as mainQueue
import platform.posix.QOS_CLASS_USER_INITIATED
import kotlin.time.ExperimentalTime

private const val TAG = "CameraViewController"

/**
 * This is built using apples AVFoundation
 * https://developer.apple.com/documentation/avfoundation/avcam-building-a-camera-app
 */
internal class CameraViewController(
    val device: AVCaptureDevice,
    val isEagerInit: Boolean = false,
    val isReadyCallback: (isReady: Boolean) -> Unit = {}
) : UIViewController(nibName = null, bundle = null) {

    private val session: DecoratedCaptureSession = DecoratedCaptureSession()
    private var previewLayer: AVCaptureVideoPreviewLayer? = null

    private val photoOutput: AVCapturePhotoOutput = AVCapturePhotoOutput()

    val zoomable = object : Zoomable {
        override fun getZoom(): Float = session.currentZoom()?.toFloat() ?: 2F
        override fun getZoomRange(): ClosedRange<Float> = (1f..30f)
    }

    override fun viewDidLoad() {
        super.viewDidLoad()
        view.setBackgroundColor(UIColor.blackColor)
        addObservers()

        runAsync(backgroundQueue(QOS_CLASS_USER_INITIATED.toLong(), 0uL)) {
            session.addInputDevice(device)
            session.addOutputExt(photoOutput)
            
            session.sessionPreset = AVCaptureSessionPresetPhoto
            photoOutput.setHighResolutionCaptureEnabled(true)

            if (isEagerInit) {
                startSession()
            }
        }

        setupCameraPreview()
    }

    override fun viewDidLayoutSubviews() {
        super.viewDidLayoutSubviews()
        previewLayer?.frame = view.bounds
    }

    override fun viewWillDisappear(animated: Boolean) {
        super.viewWillDisappear(animated)
        NSNotificationCenter.defaultCenter.removeObserver(this)
    }

    fun getDisplayZoom(zoom: Float): Float {
        return zoom * session.displayZoomFactor().toFloat()
    }

    fun setZoom(zoom: Float, isDisplayZoom: Boolean = false) {
        session.runConfigurationChange { device ->
            val displayFactor = if (isDisplayZoom) session.displayZoomFactor() else 1.0
            val newZoom = zoom.div(displayFactor)

            device.videoZoomFactor = newZoom
        }
    }

    fun captureImage(onResult: (NSData) -> Unit) {
        val captureSettings = AVCapturePhotoSettings()
        val position = session.getCurrentPosition()

        captureSettings.flashMode = session.getCurrentFlash()

        if (position == AVCaptureDevicePositionFront) {
            photoOutput.connectionWithMediaType(AVMediaTypeVideo)?.apply {
                automaticallyAdjustsVideoMirroring()
                setVideoMirrored(true)
            }
        }
        photoOutput.capturePhotoWithSettings(
            settings = captureSettings,
            delegate = object : NSObject(), AVCapturePhotoCaptureDelegateProtocol {
                override fun captureOutput(
                    output: AVCapturePhotoOutput,
                    didFinishProcessingPhoto: AVCapturePhoto,
                    error: NSError?
                ) {
                    if (error != null) {
                        Logger.e(tag = TAG, messageString = "Error capturing iOS photo")
                        return
                    }

                    Logger.i(
                        tag = TAG,
                        messageString = "iOS Photo Captured. Sending as byte array."
                    )

                    val data: NSData = didFinishProcessingPhoto.fileDataRepresentation() ?: return
                    onResult(data)
                }
            }
        )
    }

    fun setFocus(originX: Float, originY: Float) {
        if (!session.canFocus() || !session.canSetExposure()) return

        session.runConfigurationChange {
            session.setFocusPoint(originX.toDouble(), originY.toDouble())
            session.setExposurePoint(originX.toDouble(), originY.toDouble())
        }
    }

    fun startSession() {
        session.startRunning()
    }

    fun stopSession() {
        session.stopRunning()
    }

    fun getDisplayZoomRange(): ClosedRange<Float> = session.getZoomRangeForDisplay()

    fun setFlashMode(flashMode: AVCaptureFlashMode) {
        if (session.isStopped()) return
        session.runConfigurationChange { device ->
            if (device.hasFlash) device.flashMode = flashMode
        }
    }

    private fun setupCameraPreview() {
        val layer = AVCaptureVideoPreviewLayer(session = session).apply {
            frame = view.bounds
            videoGravity = AVLayerVideoGravityResizeAspectFill
            view.layer.addSublayer(this)
        }
        this.previewLayer = layer
    }

    private fun addObservers() {
        this.addSessionStartedObserver(session)
        this.addSessionStoppedObserver(session)
    }

    @ObjCAction
    fun sessionDidStartRunningWithNotification(notification: NSNotification) {
        runAsync(mainQueue()) { isReadyCallback?.invoke(true) }
    }

    @ObjCAction
    fun sessionDidStopRunningWithNotification(notification: NSNotification) {
        runAsync(mainQueue()) { isReadyCallback?.invoke(false) }
    }

    private fun CameraViewController.addSessionStartedObserver(session: AVCaptureSession) {
        NSNotificationCenter.defaultCenter.addObserver(
            observer = this,
            selector = NSSelectorFromString("sessionDidStartRunningWithNotification:"),
            name = AVCaptureSessionDidStartRunningNotification,
            `object` = session
        )
    }

    private fun CameraViewController.addSessionStoppedObserver(session: AVCaptureSession) {
        NSNotificationCenter.defaultCenter.addObserver(
            observer = this,
            selector = NSSelectorFromString("sessionDidStopRunningWithNotification:"),
            name = AVCaptureSessionDidStopRunningNotification,
            `object` = session
        )
    }
}