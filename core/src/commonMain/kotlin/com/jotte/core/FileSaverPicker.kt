package com.jotte.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.rememberCoroutineScope
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.dialogs.compose.SaverResultLauncher
import io.github.vinceglb.filekit.dialogs.compose.rememberFileSaverLauncher
import io.github.vinceglb.filekit.readBytes
import kotlinx.coroutines.launch

@Composable
fun rememberFileSaverPicker(
    src: PlatformFile?,
    onSuccess: (src: PlatformFile) -> Unit,
    onFailure: (src: PlatformFile, throwable: Throwable) -> Unit
): SaverResultLauncher {
    val scope = rememberCoroutineScope()

    return key(src) {
        rememberFileSaverLauncher { file ->
            if (file != null) {
                scope.launch {
                    runCatching { src!!.readBytes() }
                        .mapCatching { file.safeWrite(it) }
                        .onSuccess { onSuccess(file) }
                        .onFailure { onFailure(file, it) }
                }
            }
        }
    }
}