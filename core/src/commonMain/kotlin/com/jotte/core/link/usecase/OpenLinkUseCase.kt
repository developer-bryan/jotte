package com.jotte.core.link.usecase

expect class OpenLinkUseCase() {
    operator fun invoke(uri: String): Boolean
}
