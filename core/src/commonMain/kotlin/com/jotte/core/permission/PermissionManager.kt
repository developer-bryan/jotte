package com.jotte.core.permission

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

interface PermissionManager {
    @Composable
    fun providePermission(
        permission: Permission,
        onResult: (PermissionResult) -> Unit
    )

    @Composable
    fun navigateToSettings()
}

@Composable
fun rememberPermission(
    permission: Permission,
    shouldCheckPermission: MutableState<Boolean>,
    shouldLaunchSettingsScreen: MutableState<Boolean> = remember { mutableStateOf(false) },
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit
) {

    val permissionManager: PermissionManager = koinInject()
    val scope = rememberCoroutineScope()

    if (shouldLaunchSettingsScreen.value) {
        shouldLaunchSettingsScreen.value = false
        permissionManager.navigateToSettings()
    }

    if (shouldCheckPermission.value) {
        permissionManager.providePermission(
            permission = permission,
            onResult = {
                scope.launch(Dispatchers.Main) {
                    when (it) {
                        PermissionResult.Allowed -> onPermissionGranted()
                        else -> onPermissionDenied()
                    }

                    shouldCheckPermission.value = false
                }
            }
        )
    }

}