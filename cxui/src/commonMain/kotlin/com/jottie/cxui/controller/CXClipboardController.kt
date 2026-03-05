package com.jottie.cxui.controller

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.buildAnnotatedString
import com.jottie.cxui.Res
import com.jottie.cxui.controller.CXToastController
import com.jottie.cxui.content_copied_to_clipboard

class CXClipboardController(
    private val clipboard: ClipboardManager,
    private val toastState: CXToastController,
) {

    fun copyToClipboard(content: String) {
        val msgString = buildAnnotatedString { append(content) }
        clipboard.setText(msgString)
        toastState.show(Res.string.content_copied_to_clipboard)
    }

}

@Composable
fun rememberClipboardController(toastState: CXToastController): CXClipboardController {
    val clipboard = LocalClipboardManager.current

    return remember {
        CXClipboardController(clipboard, toastState)
    }
}