package com.jotte.core.link.model

data class AppLink(
    val name: String?,
    val link: String,
    val scheme: AppLinkScheme
) {

    val uri: String
        get() = scheme.scheme + link

}