package com.jotte.core.link.model

sealed class AppLinkScheme(val scheme: String) {

    data object Web : AppLinkScheme("https://")

    data object Phone : AppLinkScheme("tel:")

    data object Email : AppLinkScheme("mailto:")

}