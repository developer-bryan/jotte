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
import com.jotte.whiteboard.usecase.UpdateWhiteboardUseCase
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.ImageFormat
import io.github.vinceglb.filekit.dialogs.compose.util.encodeToByteArray
import io.github.vinceglb.filekit.write
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class WhiteboardViewModel(
    private val getWhiteboardUseCase: GetWhiteboardUseCase,
    private val updateWhiteboardUseCase: UpdateWhiteboardUseCase,
    private val mapWhiteboardPathUseCase: MapWhiteboardPathUseCase,
    private val downloadMediaUseCase: DownloadMediaUseCase,
    private val downloadFileName: String
) : ViewModel() {

    val event = Channel<WhiteboardEvent>(capacity = UNLIMITED)

    private var snapshot = MutableStateFlow<ArrayDeque<WhiteboardPath>>(ArrayDeque())

    private val _paths = MutableStateFlow<ArrayDeque<WhiteboardPath>>(ArrayDeque())
    val paths: Flow<ArrayDeque<WhiteboardPath>> = _paths

    val hasUnsavedChanges =
        combine(
            flow = snapshot,
            flow2 = paths,
            transform = { snapshot, current -> current.lastOrNull() != snapshot.lastOrNull() }
        )

    val undoEnabled = paths.map { it.isNotEmpty() }

    fun saveWhiteboardToGallery(snapshot: ImageBitmap) {
        viewModelScope.launch(
            context =
                CoroutineExceptionHandler { _, error ->
                    println(error)
                    event.trySend(WhiteboardEvent.OnMediaDownloadFailure)
                },
            block = {
                val file = FileKit.cacheFile(downloadFileName)
                val bytes = snapshot.encodeToByteArray(ImageFormat.PNG)

                file.write(bytes)

                downloadMediaUseCase.invoke(
                    file = file,
                    onSuccess = { event.trySend(WhiteboardEvent.OnMediaDownloaded) },
                    onFailure = { event.trySend(WhiteboardEvent.OnMediaDownloadFailure) }
                )
            }
        )
    }

    fun loadPaths() {
        viewModelScope.launch(
            context =
                CoroutineExceptionHandler { _, error ->
                    event.trySend(WhiteboardEvent.OnWhiteboardLoadError)
                    println(error)
                },
            block = {
                getWhiteboardUseCase()
                    ?.paths
                    ?.map(mapWhiteboardPathUseCase::invoke)
                    ?.let {
                        val deque = ArrayDeque(it)
                        snapshot.emit(deque)
                        _paths.emit(deque)
                    }
            }
        )
    }

    fun addNewPath(path: WhiteboardPath) {
        viewModelScope.launch(
            context = CoroutineExceptionHandler { _, error -> println(error) },
            block = {
                _paths.update { ArrayDeque(it).also { it.addLast(path) } }
            }
        )
    }

    fun undo() {
        viewModelScope.launch(
            context = CoroutineExceptionHandler { _, error -> println(error) },
            block = {
                if (_paths.value.isNotEmpty()) {
                    _paths.update { ArrayDeque(it).also { it.removeLastOrNull() } }
                }
            }
        )
    }

    fun updateWhiteboard() {
        viewModelScope.launch(
            context =
                CoroutineExceptionHandler { _, error ->
                    println(error)
                    event.trySend(WhiteboardEvent.OnSaveError)
                },
            block = {
                val paths = paths.first().toList()
                updateWhiteboardUseCase(paths)
                event.send(WhiteboardEvent.OnWhiteboardUpdated)

                loadPaths()
            }
        )
    }

}