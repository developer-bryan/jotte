package com.jotte.core.link.usecase

import android.content.Intent
import android.net.Uri
import com.jotte.core.ApplicationProvider
import com.jotte.core.link.model.AppLinkScheme

actual class OpenLinkUseCase {

    @Suppress("TooGenericExceptionCaught")
    actual operator fun invoke(uri: String): Boolean =
        try {
            val context = ApplicationProvider.getApplication()!!
            val intent = createIntent(uri)

            context.startActivity(intent)
            true
        } catch (exception: Exception) {
            false
        }

    fun createIntent(uri: String): Intent =
        with(Uri.parse(uri)) {
            Intent(getIntentAction(), this)
                .apply { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) }
        }

    fun Uri.getIntentAction(): String =
        when {
            this.scheme?.contains(AppLinkScheme.Web.scheme) == true -> Intent.ACTION_DIAL
            this.scheme?.contains(AppLinkScheme.Phone.scheme) == true -> Intent.ACTION_VIEW
            this.scheme?.contains(AppLinkScheme.Email.scheme) == true -> Intent.ACTION_SENDTO
            else -> error("Missing scheme")
        }
}