package com.jotte.core.link.usecase

import platform.Foundation.NSURL
import platform.UIKit.UIApplication

actual class OpenLinkUseCase {

    actual operator fun invoke(uri: String): Boolean {
        val application = UIApplication.sharedApplication
        val nsurl = NSURL.URLWithString(uri) ?: return false

        if (!application.canOpenURL(nsurl)) {
            return false
        }

        application.openURL(
            url = nsurl,
            options = emptyMap<Any?, Any>(),
            completionHandler = null
        )
        return true
    }

}