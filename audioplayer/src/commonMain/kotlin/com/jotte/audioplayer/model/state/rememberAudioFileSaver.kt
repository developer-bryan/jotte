package com.jotte.audioplayer.model.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.rememberCoroutineScope
import com.jotte.core.safeWrite
import com.jotte.cxui.Res
import com.jotte.cxui.composition.LocalToastController
import com.jotte.cxui.generic_error_message
import com.jotte.cxui.media_download
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