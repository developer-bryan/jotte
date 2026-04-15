package com.jotte.core.link.model

data class AppLink(
    val link: String,
    val scheme: AppLinkScheme,
    val name: String? = null
) {

    val uri: String
        get() = scheme.scheme + link

}