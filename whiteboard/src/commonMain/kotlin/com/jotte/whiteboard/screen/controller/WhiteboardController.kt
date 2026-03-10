package com.jotte.whiteboard.screen.controller

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.jotte.cxui.Res
import com.jotte.cxui.composition.LocalToastController
import com.jotte.cxui.controller.CXToastController
import com.jotte.cxui.media_download

internal class WhiteboardController(
    private val toastController: CXToastController
) {

    fun onMediaDownloaded() {
        toastController.show(Res.string.media_download)
    }

    fun onMediaDownloadFailure() {
        toastController.showError()
    }

}

@Composable
internal fun rememberWhiteboardController(): WhiteboardController {

    val toastController = LocalToastController.current

    return remember {
        WhiteboardController(toastController = toastController)
    }

}