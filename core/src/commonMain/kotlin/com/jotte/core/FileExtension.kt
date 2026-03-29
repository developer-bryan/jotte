@file:Suppress("MatchingDeclarationName")

package com.jotte.core

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.decodeToImageBitmap
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.absolutePath
import io.github.vinceglb.filekit.cacheDir
import io.github.vinceglb.filekit.copyTo
import io.github.vinceglb.filekit.delete
import io.github.vinceglb.filekit.filesDir
import io.github.vinceglb.filekit.path
import io.github.vinceglb.filekit.readBytes
import io.github.vinceglb.filekit.saveImageToGallery
import io.github.vinceglb.filekit.write
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

fun FileKit.storageFile(name: String): PlatformFile = PlatformFile(filesDir, name)

fun FileKit.cacheFile(name: String): PlatformFile = PlatformFile(cacheDir, name)

suspend fun FileKit.copyCacheToStorage(fileName: String): Result<Boolean> =
    withContext(Dispatchers.IO) {
        runCatching {
            val cache = cacheFile(fileName)
            val storage = storageFile(fileName)

            cache.copyTo(storage)
            true
        }
    }

fun PlatformFile.isCacheFile(): Boolean {
    val cachePath = FileKit.cacheDir.absolutePath()
    val filePath = this.absolutePath()
    return filePath.startsWith(cachePath)
}

fun FileKit.toStoragePath(fileName: String): String = storageFile(fileName).path

suspend fun PlatformFile.safeWrite(bytes: ByteArray): Result<Boolean> =
    runCatching {
        write(bytes)
        true
    }

suspend fun PlatformFile.safeWrite(file: PlatformFile): Result<Boolean> =
    runCatching {
        write(file)
        true
    }

suspend fun FileKit.safeSaveImageToGallery(
    bytes: ByteArray,
    fileName: String,
): Result<Boolean> =
    runCatching {
        saveImageToGallery(bytes, fileName)
        true
    }

suspend fun PlatformFile.safeDelete(): Result<Boolean> =
    runCatching {
        delete(false)
        true
    }

suspend fun FileKit.readBitmap(fileName: String): ImageBitmap =
    withContext(Dispatchers.IO) {
        val bytes = FileKit.storageFile(fileName).readBytes()
        bytes.decodeToImageBitmap()
    }