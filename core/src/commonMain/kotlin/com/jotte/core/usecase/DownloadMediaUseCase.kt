package com.jotte.core.usecase

import androidx.compose.runtime.staticCompositionLocalOf
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.exists
import io.github.vinceglb.filekit.saveImageToGallery
import io.github.vinceglb.filekit.toKotlinxIoPath

val LocalDownloadMediaUseCase = staticCompositionLocalOf<DownloadMediaUseCase> { error("missing downloader") }

class DownloadMediaUseCase(val imageRegex: Regex) {

    suspend operator fun invoke(
        file: PlatformFile,
        onSuccess: (Boolean) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {

        runCatching {
            val path = file.toKotlinxIoPath()
            if (file.exists() && path.name.contains(imageRegex)) {
                FileKit.saveImageToGallery(file)
                true
            } else throw IllegalStateException("invalid file type")
        }
            .onSuccess(onSuccess)
            .onFailure(onFailure)

    }

}
