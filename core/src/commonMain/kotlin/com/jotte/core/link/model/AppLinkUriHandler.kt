package com.jotte.core.link.model

import androidx.compose.ui.platform.UriHandler
import com.jotte.core.link.usecase.OpenLinkUseCase

class AppLinkUriHandler(val openLinkUseCase: OpenLinkUseCase) : UriHandler {
    override fun openUri(uri: String) {
        openLinkUseCase(uri)
    }
}