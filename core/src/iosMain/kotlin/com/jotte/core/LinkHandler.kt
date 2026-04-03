package com.jotte.core

import platform.Foundation.NSURL
import platform.UIKit.UIApplication
import kotlin.collections.emptyMap

actual class LinkHandler {

    val application = UIApplication.sharedApplication

    actual fun openUrl(url: String): Boolean {

        val safeUrl =
            if (url.startsWith("http://") || url.startsWith("https://")) {
                url
            } else {
                "https://$url"
            }

        val nsurl = NSURL.URLWithString(safeUrl) ?: return false
        return handleNSURL(nsurl)
    }

    actual fun handlePhoneNumber(phoneNumber: String): Boolean {
        val nsurl = NSURL(string = "tel:$phoneNumber")
        return handleNSURL(nsurl)
    }

    actual fun handleEmail(email: String): Boolean {
        val nsurl = NSURL.URLWithString("mailto:$email") ?: return false
        return handleNSURL(nsurl)
    }

    private fun handleNSURL(nsurl: NSURL): Boolean {
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