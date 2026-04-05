package com.jotte.core.usecase

import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.exists
import io.github.vinceglb.filekit.saveImageToGallery
import io.github.vinceglb.filekit.toKotlinxIoPath

class SaveFileToGalleryUseCase(val imageRegex: Regex) {

    suspend operator fun invoke(
        file: PlatformFile,
        onSuccess: (Boolean) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {

        runCatching {
            val path = file.toKotlinxIoPath()
            check(file.exists())
            check(path.name.contains(imageRegex))
            FileKit.saveImageToGallery(file)
            true
        }.onSuccess(onSuccess)
            .onFailure(onFailure)

    }

}
