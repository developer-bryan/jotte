package com.jotte.room.model.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.UriHandler
import com.jotte.core.link.usecase.OpenLinkUseCase
import com.jotte.cxui.Res
import com.jotte.cxui.composition.LocalToastController
import com.jotte.cxui.invalid_link_msg
import org.koin.compose.koinInject

@Composable
fun rememberRichTextUriHandler(): UriHandler {

    val openLinkUseCase: OpenLinkUseCase = koinInject()
    val toastController = LocalToastController.current

    return remember {
        object: UriHandler {
            override fun openUri(uri: String) {
                val didOpen = openLinkUseCase(uri)

                if (!didOpen) {
                    toastController.showError(Res.string.invalid_link_msg)
                }
            }
        }
    }

}