package com.jotte.core

import androidx.compose.runtime.staticCompositionLocalOf
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.exists
import io.github.vinceglb.filekit.readBytes
import io.github.vinceglb.filekit.saveImageToGallery
import io.github.vinceglb.filekit.toKotlinxIoPath

val LocalFileDownloader = staticCompositionLocalOf<FileDownloader> { error("missing downloader") }

class FileDownloader(val imageRegex: Regex) {

    suspend fun downloadImageFile(
        file: PlatformFile,
        onSuccess: (Boolean) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {

        runCatching {
            val path = file.toKotlinxIoPath()
            if (file.exists() && path.name.contains(imageRegex)) {
                FileKit.saveImageToGallery(file)
                true
            } else false
        }
            .onSuccess(onSuccess)
            .onFailure(onFailure)

    }

}
