@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.jotte.core.permission

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.jotte.core.ApplicationProvider

@Suppress("ACTUAL_WITHOUT_EXPECT")
actual class PermissionManagerImpl : PermissionManager {

    @SuppressLint("ComposableNaming")
    @Composable
    actual override fun providePermission(
        permission: Permission,
        onResult: (PermissionResult) -> Unit
    ) {
        when (permission) {
            Permission.PhotoWrite -> onResult(PermissionResult.Allowed) // We do not need to request at runtime
            Permission.Camera -> requestPermission(Manifest.permission.CAMERA, onResult)
            Permission.Audio -> requestPermission(Manifest.permission.RECORD_AUDIO, onResult)
            Permission.Notifications -> {
                if (Build.VERSION.SDK_INT < 33) {
                    onResult(PermissionResult.Allowed)
                } else {
                    requestPermission(Manifest.permission.POST_NOTIFICATIONS, onResult)
                }
            }
        }
    }

    @Composable
    private fun requestPermission(
        permission: String,
        onResult: (PermissionResult) -> Unit
    ) {

        val context = LocalContext.current
        val level = ContextCompat.checkSelfPermission(context, permission)

        if (level == PackageManager.PERMISSION_GRANTED) {
            onResult(PermissionResult.Allowed)
            return
        }

        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = { isGranted ->
                if (isGranted) {
                    onResult(PermissionResult.Allowed)
                } else {
                    onResult(PermissionResult.Rejected)
                }
            }
        )

        if (level == PackageManager.PERMISSION_DENIED) {
            LaunchedEffect(Unit) { launcher.launch(permission) }
        }

    }

    @Composable
    actual override fun navigateToSettings() {
        val context = LocalContext.current
        val intent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", context.packageName, null)
        )
        context.startActivity(intent)
    }

    fun hasPermission(permission: String): Boolean = ContextCompat.checkSelfPermission(
        ApplicationProvider.getApplication()!!,
        permission
    ) == PackageManager.PERMISSION_GRANTED

}