package com.jotte.whiteboard.viewmodel

import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jotte.core.cacheFile
import com.jotte.core.usecase.DownloadMediaUseCase
import com.jotte.message.usecase.GetWhiteboardUseCase
import com.jotte.whiteboard.model.event.WhiteboardEvent
import com.jotte.whiteboard.model.state.WhiteboardPath
import com.jotte.whiteboard.usecase.MapWhiteboardPathUseCase
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.ImageFormat
import io.github.vinceglb.filekit.dialogs.compose.util.encodeToByteArray
import io.github.vinceglb.filekit.write
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

internal class WhiteboardViewModel(
    private val getWhiteboardUseCase: GetWhiteboardUseCase,
    private val mapWhiteboardPathUseCase: MapWhiteboardPathUseCase,
    private val downloadMediaUseCase: DownloadMediaUseCase,
    private val downloadFileName: String
) : ViewModel() {

    private val _paths = MutableStateFlow<List<WhiteboardPath>>(emptyList())
    private val _newPaths = MutableStateFlow<List<WhiteboardPath>>(emptyList())

    val event = Channel<WhiteboardEvent>(capacity = UNLIMITED)
    val paths = _paths.combine(_newPaths) { savedPaths, newPaths -> (savedPaths + newPaths) }
    val hasUnsavedPaths = _newPaths.map { it.isNotEmpty() }

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

    fun loadPaths() {
        viewModelScope.launch(
            context = CoroutineExceptionHandler { _, error ->
                event.trySend(WhiteboardEvent.OnWhiteboardLoadError)
                println(error)
            },
            block = {
                getWhiteboardUseCase()
                    ?.paths
                    ?.map(mapWhiteboardPathUseCase::invoke)
                    ?.let { _paths.emit(it) }
            }
        )
    }

    fun addNewPath(path: WhiteboardPath) {
        viewModelScope.launch(
            context = CoroutineExceptionHandler { _, error -> println(error) },
            block = {
                val newPaths = _newPaths.value
                val updatedNewPaths = ArrayList(newPaths)
                updatedNewPaths.add(path)

                _newPaths.emit(updatedNewPaths)
            }
        )
    }

}