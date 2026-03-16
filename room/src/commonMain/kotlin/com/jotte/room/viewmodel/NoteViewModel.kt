@file:OptIn(ExperimentalCoroutinesApi::class)

package com.jotte.room.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jotte.core.datetime.usecase.GetFullDateUseCase
import com.jotte.cxui.soundeffect.SoundEffect
import com.jotte.cxui.soundeffect.SoundEffectsPlayer
import com.jotte.message.data.MediaDto
import com.jotte.message.repository.NoteRepository
import com.jotte.message.repository.RoomRepository
import com.jotte.message.usecase.DeleteMediaUseCase
import com.jotte.room.model.event.RoomEvent
import com.jotte.message.usecase.DeleteNoteUseCase
import com.jotte.room.usecase.MapNoteUseCase
import com.jotte.room.model.state.RoomMetricsState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

internal class NoteViewModel(
    roomId: Long,
    roomRepository: RoomRepository,
    noteRepository: NoteRepository,
    private val mapNoteUseCase: MapNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val getFullDateUseCase: GetFullDateUseCase,
    private val deleteMediaUseCase: DeleteMediaUseCase,
    private val soundEffectsPlayer: SoundEffectsPlayer
) : ViewModel() {

    val event = Channel<RoomEvent>(UNLIMITED)

    val room = roomRepository.observeRoom(roomId)

    val roomName = room.filterNotNull().map { it.name }

    val notes = noteRepository
        .observeNotes(roomId)
        .map { notes -> notes.map { msg -> mapNoteUseCase(msg) } }

    val roomMetricsState = combine(
        flow = room,
        flow2 = notes,
        transform = { room, notes ->

            val createdOn = room?.createdOn?.let { getFullDateUseCase(it).date } ?: ""

            val modifiedOn = room?.modifiedOn?.let {
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