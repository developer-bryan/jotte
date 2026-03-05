package com.jottie.core.permission

sealed interface PermissionResult {
    data object Allowed : PermissionResult
    data object Rejected : PermissionResult
    data object Disabled : PermissionResult
    data object UnDetermined: PermissionResult
}