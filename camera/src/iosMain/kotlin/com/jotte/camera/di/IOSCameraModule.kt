@file:OptIn(ExperimentalForeignApi::class)

package com.jotte.camera.di

import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.core.qualifier.StringQualifier
import org.koin.dsl.module
import platform.AVFoundation.AVCaptureDevice
import platform.AVFoundation.AVCaptureDeviceDiscoverySession
import platform.AVFoundation.AVCaptureDevicePositionBack
import platform.AVFoundation.AVCaptureDevicePositionFront
import platform.AVFoundation.AVCaptureDeviceType
import platform.AVFoundation.AVCaptureDeviceTypeBuiltInDualCamera
import platform.AVFoundation.AVCaptureDeviceTypeBuiltInDualWideCamera
import platform.AVFoundation.AVCaptureDeviceTypeBuiltInDuoCamera
import platform.AVFoundation.AVCaptureDeviceTypeBuiltInTripleCamera
import platform.AVFoundation.AVCaptureDeviceTypeBuiltInTrueDepthCamera
import platform.AVFoundation.AVCaptureDeviceTypeBuiltInUltraWideCamera
import platform.AVFoundation.AVCaptureDeviceTypeBuiltInWideAngleCamera
import platform.AVFoundation.AVMediaTypeVideo

internal fun supportedSelfieCameras() = StringQualifier("supportedSelfieCameras")

internal fun supportedRearCameras() = StringQualifier("supportedRearCamera")

internal fun selfieDevice() = StringQualifier("selfieDevice")

internal fun rearDevice() = StringQualifier("rearDevice")

internal fun provideIOSCameraModule() =
    module {

        single<List<AVCaptureDeviceType>>(
            qualifier = supportedSelfieCameras(),
            definition = {
                listOf(
                    AVCaptureDeviceTypeBuiltInTrueDepthCamera,
                    AVCaptureDeviceTypeBuiltInDualCamera,
                    AVCaptureDeviceTypeBuiltInWideAngleCamera,
                    AVCaptureDeviceTypeBuiltInDualWideCamera,
                    AVCaptureDeviceTypeBuiltInDuoCamera
                )
            }
        )

        single<List<AVCaptureDeviceType>>(
            qualifier = supportedRearCameras(),
            definition = {
                listOf(
                    AVCaptureDeviceTypeBuiltInTripleCamera,
                    AVCaptureDeviceTypeBuiltInTrueDepthCamera,
                    AVCaptureDeviceTypeBuiltInDualCamera,
                    AVCaptureDeviceTypeBuiltInWideAngleCamera,
                    AVCaptureDeviceTypeBuiltInDualWideCamera,
                    AVCaptureDeviceTypeBuiltInUltraWideCamera,
                    AVCaptureDeviceTypeBuiltInDuoCamera
                )
            }
        )

        factory<AVCaptureDevice>(
            qualifier = selfieDevice(),
            definition = {
                val devices = get<List<AVCaptureDeviceType>>(supportedSelfieCameras())
                AVCaptureDeviceDiscoverySession
                    .discoverySessionWithDeviceTypes(
                        deviceTypes = devices,
                        mediaType = AVMediaTypeVideo,
                        position = AVCaptureDevicePositionFront
                    ).devices
                    .first() as AVCaptureDevice
            }
        )

        factory<AVCaptureDevice>(
            qualifier = rearDevice(),
            definition = {
                val devices = get<List<AVCaptureDeviceType>>(supportedRearCameras())
                AVCaptureDeviceDiscoverySession
                    .discoverySessionWithDeviceTypes(
                        deviceTypes = devices,
                        mediaType = AVMediaTypeVideo,
                        position = AVCaptureDevicePositionBack
                    ).devices
                    .first() as AVCaptureDevice
            }
        )

    }