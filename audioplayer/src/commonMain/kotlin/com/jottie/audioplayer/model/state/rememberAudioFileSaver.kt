package com.jottie.audioplayer.model.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.rememberCoroutineScope
import com.jottie.core.safeWrite
import com.jottie.cxui.Res
import com.jottie.cxui.composition.LocalToastController
import com.jottie.cxui.generic_error_message
import com.jottie.cxui.media_download
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.dialogs.compose.SaverResultLauncher
import io.github.vinceglb.filekit.dialogs.compose.rememberFileSaverLauncher
import io.github.vinceglb.filekit.readBytes
import kotlinx.coroutines.launch

@Composable
fun rememberAudioFileSaver(src: PlatformFile?): SaverResultLauncher {
    val scope = rememberCoroutineScope()
    val toastState = LocalToastController.current

    return key(src) {
        rememberFileSaverLauncher { file ->
            if (file != null) {
                scope.launch {
                    runCatching { src!!.readBytes() }
                        .mapCatching { file.safeWrite(it) }
                        .onSuccess { toastState.show(Res.string.media_download) }
                        .onFailure { toastState.show(Res.string.generic_error_message) }
                }
            }
        }
    }
}