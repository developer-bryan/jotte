@file:OptIn(ExperimentalCoroutinesApi::class)

package com.jotte.room.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jotte.core.datetime.usecase.GetFullDateUseCase
import com.jotte.core.usecase.SaveFileToGalleryUseCase
import com.jotte.cxui.soundeffect.SoundEffect
import com.jotte.cxui.soundeffect.SoundEffectsPlayer
import com.jotte.data.persistence.data.MediaDto
import com.jotte.data.repository.NoteRepository
import com.jotte.data.repository.RoomRepository
import com.jotte.data.usecase.DeleteMediaUseCase
import com.jotte.data.usecase.DeleteNoteUseCase
import com.jotte.room.model.event.RoomEvent
import com.jotte.room.model.state.RoomMetricsState
import com.jotte.room.usecase.MapNoteUseCase
import io.github.vinceglb.filekit.PlatformFile
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

internal class RoomViewModel(
    roomId: Long,
    roomRepository: RoomRepository,
    noteRepository: NoteRepository,
    private val mapNoteUseCase: MapNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val getFullDateUseCase: GetFullDateUseCase,
    private val deleteMediaUseCase: DeleteMediaUseCase,
    private val saveFileToGalleryUseCase: SaveFileToGalleryUseCase,
    private val soundEffectsPlayer: SoundEffectsPlayer
) : ViewModel() {

    val event = Channel<RoomEvent>(UNLIMITED)

    val room = roomRepository.observeRoom(roomId)

    val roomName = room.filterNotNull().map { it.name }

    val notes =
        noteRepository
            .observeNotes(roomId)
            .map { notes -> notes.map { msg -> mapNoteUseCase(msg) } }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    val roomMetricsState =
        combine(
            flow = room,
            flow2 = notes,
            transform = { room, notes ->

                val createdOn = room?.createdOn?.let { getFullDateUseCase(it).date } ?: ""

                val modifiedOn =
                    room?.modifiedOn?.let {
                        val fullDate = getFullDateUseCase(it)
                        fullDate.date + "@" + fullDate.time
                    } ?: ""

                RoomMetricsState(
                    totalNotes = notes.size,
                    createdOn = createdOn,
                    modifiedOn = modifiedOn
                )
            }
        )

    fun deleteFile(file: MediaDto) {
        viewModelScope.launch {
            deleteMediaUseCase(file)
                .onSuccess {
                    soundEffectsPlayer.playSound(SoundEffect.SoundEffectRemoval)
                    event.send(RoomEvent.OnMediaDeleted(file.mediaId))
                }.onFailure { /* emit generic error */ }
        }
    }

    fun deleteNote(noteId: Long) {
        viewModelScope.launch {
            deleteNoteUseCase(noteId)
                .onSuccess {
                    soundEffectsPlayer.playSound(SoundEffect.SoundEffectRemoval)
                    event.send(RoomEvent.OnNoteDeleted)
                }.onFailure { /* handle */ }
        }
    }

    fun saveMediaToGallery(file: PlatformFile) {
        viewModelScope.launch {
            saveFileToGalleryUseCase(
                file = file,
                onSuccess = { event.trySend(RoomEvent.OnFileSavedToGallery) },
                onFailure = { event.trySend(RoomEvent.OnFileSavedToGalleryError) }
            )
        }
    }

}