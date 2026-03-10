package com.jotte.whiteboard.viewmodel

import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jotte.core.cacheFile
import com.jotte.core.usecase.DownloadMediaUseCase
import com.jotte.whiteboard.model.event.WhiteboardEvent
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.ImageFormat
import io.github.vinceglb.filekit.dialogs.compose.util.encodeToByteArray
import io.github.vinceglb.filekit.write
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.launch

internal class WhiteboardViewModel(
    private val downloadMediaUseCase: DownloadMediaUseCase,
    private val downloadFileName: String
): ViewModel() {

    val event = Channel<WhiteboardEvent>(capacity = UNLIMITED)

    fun saveWhiteboardSnapshot(snapshot: ImageBitmap) {
        viewModelScope.launch {
            val file = FileKit.cacheFile(downloadFileName)
            val bytes = snapshot.encodeToByteArray(ImageFormat.PNG)

            file.write(bytes)

            downloadMediaUseCase.invoke(
                file = file,
                onSuccess = { event.trySend(WhiteboardEvent.OnMediaDownloaded) },
                onFailure = { event.trySend(WhiteboardEvent.OnMediaDownloadFailure) }
            )
        }
    }

}