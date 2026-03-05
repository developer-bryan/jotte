package com.jottie.core

import androidx.compose.runtime.staticCompositionLocalOf

val LocalLinkHandler = staticCompositionLocalOf { LinkHandler() }

expect class LinkHandler() {
    fun openUrl(url: String): Boolean
    fun handlePhoneNumber(phoneNumber: String): Boolean
}