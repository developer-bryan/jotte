@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING", "ComposableNaming")
package com.jottie.core.permission

import androidx.compose.runtime.Composable
import platform.AVFAudio.AVAudioSession
import platform.AVFAudio.AVAudioSessionRecordPermissionDenied
import platform.AVFAudio.AVAudioSessionRecordPermissionGranted
import platform.AVFAudio.AVAudioSessionRecordPermissionUndetermined
import platform.AVFoundation.AVAuthorizationStatusAuthorized
import platform.AVFoundation.AVAuthorizationStatusDenied
import platform.AVFoundation.AVAuthorizationStatusNotDetermined
import platform.AVFoundation.AVCaptureDevice
import platform.AVFoundation.AVMediaTypeVideo
import platform.AVFoundation.authorizationStatusForMediaType
import platform.AVFoundation.requestAccessForMediaType
import platform.Foundation.NSURL
import platform.Photos.PHAccessLevelAddOnly
import platform.Photos.PHAuthorizationStatusAuthorized
import platform.Photos.PHAuthorizationStatusDenied
import platform.Photos.PHAuthorizationStatusNotDetermined
import platform.Photos.PHAuthorizationStatusRestricted
import platform.Photos.PHPhotoLibrary
import platform.UIKit.UIApplication
import platform.UserNotifications.UNAuthorizationOptionAlert
import platform.UserNotifications.UNAuthorizationOptionBadge
import platform.UserNotifications.UNAuthorizationOptionSound
import platform.UserNotifications.UNAuthorizationStatusAuthorized
import platform.UserNotifications.UNAuthorizationStatusDenied
import platform.UserNotifications.UNAuthorizationStatusEphemeral
import platform.UserNotifications.UNAuthorizationStatusNotDetermined
import platform.UserNotifications.UNUserNotificationCenter

actual class PermissionManagerImpl : PermissionManager {

    @Composable
    actual override fun providePermission(
        permission: Permission,
        onResult: (PermissionResult) -> Unit
    ) {
        when (permission) {
            Permission.Camera -> provideCameraPermission(onResult)
            Permission.PhotoWrite -> providePhotoWritePermission(onResult)
            Permission.Audio -> provideAudioPermission(onResult)
            Permission.Notifications -> provideNotificationPermissions(onResult)
        }
    }

    @Composable
    private fun provideCameraPermission(onResult: (PermissionResult) -> Unit) {
        val status = AVCaptureDevice.authorizationStatusForMediaType(AVMediaTypeVideo)
        when (status) {
            AVAuthorizationStatusAuthorized -> onResult(PermissionResult.Allowed)
            AVAuthorizationStatusDenied -> onResult(PermissionResult.Disabled)
            AVAuthorizationStatusNotDetermined -> {
                AVCaptureDevice.requestAccessForMediaType(
                    mediaType = AVMediaTypeVideo,
                    completionHandler = { granted ->
                        if (granted) {
                            onResult(PermissionResult.Allowed)
                        } else {
                            onResult(PermissionResult.Rejected)
                        }
                    }
                )
            }
        }
    }

    @Composable
    private fun providePhotoWritePermission(onResult: (PermissionResult) -> Unit) {
        val permissionStatus =
            PHPhotoLibrary.authorizationStatusForAccessLevel(PHAccessLevelAddOnly)
        when (permissionStatus) {
            PHAuthorizationStatusAuthorized -> onResult(PermissionResult.Allowed)
            PHAuthorizationStatusDenied -> onResult(PermissionResult.Disabled)
            PHAuthorizationStatusRestricted -> onResult(PermissionResult.Disabled)
            PHAuthorizationStatusNotDetermined -> {
                PHPhotoLibrary.requestAuthorizationForAccessLevel(
                    accessLevel = PHAccessLevelAddOnly,
                    handler = { accessLevel ->
                        when (accessLevel) {
                            PHAuthorizationStatusAuthorized -> onResult(PermissionResult.Allowed)
                            PHAuthorizationStatusDenied -> onResult(PermissionResult.Rejected)
                        }
                    }
                )
            }
        }
    }

    @Composable
    private fun provideNotificationPermissions(onResult: (PermissionResult) -> Unit) {
        val center = UNUserNotificationCenter.currentNotificationCenter()
        val options =
            UNAuthorizationOptionAlert or UNAuthorizationOptionSound or UNAuthorizationOptionBadge

        center.getNotificationSettingsWithCompletionHandler { settings ->
            val permissionStatus = settings?.authorizationStatus
            when (permissionStatus) {
                UNAuthorizationStatusAuthorized -> onResult(PermissionResult.Allowed)
                UNAuthorizationStatusDenied -> onResult(PermissionResult.Disabled)
                UNAuthorizationStatusEphemeral -> onResult(PermissionResult.Allowed)
                UNAuthorizationStatusNotDetermined -> {
                    center.requestAuthorizationWithOptions(options) { isGranted, error ->
                        val permissionResult =
                            if (isGranted) PermissionResult.Allowed else PermissionResult.Rejected
                        onResult(permissionResult)
                    }
                }
            }
        }
    }

    @Composable
    private fun provideAudioPermission(onResult: (PermissionResult) -> Unit) {
        val audioSession = AVAudioSession.sharedInstance()
        val permissionStatus = audioSession.recordPermission

        when (permissionStatus) {
            AVAudioSessionRecordPermissionGranted -> onResult(PermissionResult.Allowed)
            AVAudioSessionRecordPermissionDenied -> onResult(PermissionResult.Disabled)
            AVAudioSessionRecordPermissionUndetermined -> {
                audioSession.requestRecordPermission { isGranted ->
                    if (isGranted) {
                        onResult(PermissionResult.Allowed)
                    } else {
                        onResult(PermissionResult.Rejected)
                    }
                }
            }
        }
    }

    @Composable
    actual override fun navigateToSettings() {
        // TODO: Update to route to the APPS setting screen
        val settingsUrl = NSURL.URLWithString("app-settings:") ?: return
        UIApplication.sharedApplication.openURL(
            url = settingsUrl,
            options = emptyMap<Any?, Any?>(),
            completionHandler = { something -> }
        )
    }
}