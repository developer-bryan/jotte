@file:OptIn(ExperimentalCoroutinesApi::class)

package com.jotte.room.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jotte.cxui.soundeffect.SoundEffect
import com.jotte.cxui.soundeffect.SoundEffectsPlayer
import com.jotte.message.data.MediaDto
import com.jotte.message.repository.NoteRepository
import com.jotte.message.repository.RoomRepository
import com.jotte.message.usecase.DeleteMediaUseCase
import com.jotte.room.model.event.RoomEvent
import com.jotte.message.usecase.DeleteNoteUseCase
import com.jotte.room.usecase.MapNoteUseCase
import com.jotte.room.model.state.RoomState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

internal class NoteViewModel(
    roomId: Long,
    roomRepository: RoomRepository,
    noteRepository: NoteRepository,
    private val mapNoteUseCase: MapNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val deleteMediaUseCase: DeleteMediaUseCase,
    private val soundEffectsPlayer: SoundEffectsPlayer
) : ViewModel() {

    val event = Channel<RoomEvent>(UNLIMITED)

    val roomState = roomRepository
        .observeRoom(roomId)
        .filterNotNull()
        .map { RoomState(it.name) }

    val notes = noteRepository
        .observeNotes(roomId)
        .map { notes -> notes.map { msg -> mapNoteUseCase(msg) } }

    fun deleteFile(file: MediaDto) {
        viewModelScope.launch {
            deleteMediaUseCase(file)
                .onSuccess {
                    soundEffectsPlayer.playSound(SoundEffect.SoundEffectRemoval)
                    event.send(RoomEvent.OnMediaDeleted(file.mediaId))
                }
                .onFailure { /* emit generic error */ }
        }
    }

    fun deleteNote(noteId: Long) {
        viewModelScope.launch {
            deleteNoteUseCase(noteId)
                .onSuccess {
                    soundEffectsPlayer.playSound(SoundEffect.SoundEffectRemoval)
                    event.send(RoomEvent.OnNoteDeleted)
                }
                .onFailure { /* handle */ }
        }
    }

}