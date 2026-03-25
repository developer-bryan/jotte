package com.jotte.core.permission

import androidx.compose.runtime.Composable

expect class PermissionManagerImpl() : PermissionManager {

    @Composable
    override fun providePermission(permission: Permission, onResult: (PermissionResult) -> Unit)

    @Composable
    override fun navigateToSettings()
}