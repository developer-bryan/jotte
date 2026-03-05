package com.jottie.core.permission

sealed interface Permission {
    data object Camera: Permission
    data object Audio: Permission
    data object PhotoWrite: Permission
    data object Notifications: Permission
}