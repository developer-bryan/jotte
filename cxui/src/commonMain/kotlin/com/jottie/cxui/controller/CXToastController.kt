package com.jottie.cxui.controller

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.jottie.cxui.Res
import com.jottie.cxui.generic_error_message
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.StringResource

class CXToastController(private val scope: CoroutineScope) {

    internal var msgRes by mutableStateOf<StringResource?>(null)

    fun showError(message: StringResource = Res.string.generic_error_message) {
        show(message)
    }

    fun show(
        message: StringResource,
        duration: Long = 2500L
    ) {
        scope.launch {
            this@CXToastController.msgRes = message
            delay(duration)
            this@CXToastController.msgRes = null
        }
    }

}

@Composable
fun rememberCXToastController(): CXToastController {
    val scope = rememberCoroutineScope()
    return remember { CXToastController(scope) }
}