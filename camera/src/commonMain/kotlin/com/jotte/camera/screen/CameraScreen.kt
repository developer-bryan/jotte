package com.jotte.camera.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.jotte.camera.screen.layout.CameraLayout
import com.jotte.camera.screen.layout.NoCameraPermissionLayout
import com.jotte.core.permission.Permission
import com.jotte.core.permission.rememberPermission
import com.jotte.cxui.Res
import com.jotte.cxui.color.CXDarkColors
import com.jotte.cxui.composition.LocalColor
import com.jotte.cxui.enable_camera_permissions
import io.github.vinceglb.filekit.PlatformFile
import org.jetbrains.compose.resources.stringResource

@Composable
fun CameraScreen(
    onCloseClicked: () -> Unit,
    onMediaCaptured: (file: PlatformFile) -> Unit
) {

    val shouldCheckCameraPermission = remember { mutableStateOf(true) }
    val shouldLaunchSettingsScreen = remember { mutableStateOf(false) }

    var isCameraPreviewEnabled by remember { mutableStateOf(false) }
    var isCameraPermissionDenied by remember { mutableStateOf(false) }

    rememberPermission(
        permission = Permission.Camera,
        shouldCheckPermission = shouldCheckCameraPermission,
        shouldLaunchSettingsScreen = shouldLaunchSettingsScreen,
        onPermissionGranted = {
            isCameraPreviewEnabled = true
            isCameraPermissionDenied = false
        },
        onPermissionDenied = {
            isCameraPreviewEnabled = false
            isCameraPermissionDenied = true
        }
    )

    CompositionLocalProvider(
        value = LocalColor provides CXDarkColors(),
        content = {
            if (isCameraPermissionDenied) {
                NoCameraPermissionLayout(
                    message = stringResource(Res.string.enable_camera_permissions),
                    onPositiveButtonClicked = { shouldLaunchSettingsScreen.value = true },
                    onNegativeButtonClicked = onCloseClicked
                )
            } else if (isCameraPreviewEnabled) {
                CameraLayout(
                    modifier = Modifier.fillMaxSize(),
                    onCloseClicked = onCloseClicked,
                    onMediaCaptured = onMediaCaptured,
                )
            }
        }
    )
}
