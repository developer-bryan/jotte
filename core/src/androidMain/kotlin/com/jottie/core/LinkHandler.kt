package com.jottie.core

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri

actual class LinkHandler {
    actual fun openUrl(url: String): Boolean {
        return try {
            val safeUrl = if (url.startsWith("http://") || url.startsWith("https://")) {
                url
            } else {
                "https://$url"
            }
            val context = ApplicationProvider.getApplication() ?: return false
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(safeUrl))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
            true
        } catch (e: ActivityNotFoundException) {
            false
        }
    }

    actual fun handlePhoneNumber(phoneNumber: String): Boolean {
        return try {
            val context = ApplicationProvider.getApplication()!!
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
            true
        } catch (e: ActivityNotFoundException) {
            false
        }
    }

}