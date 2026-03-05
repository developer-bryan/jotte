package com.jottie.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jottie.app.model.event.MainEvent
import com.jottie.cxui.soundeffect.SoundEffect
import com.jottie.cxui.soundeffect.SoundEffectsPlayer
import com.jottie.message.repository.RoomRepository
import com.jottie.message.usecase.CreateRoomUseCase
import com.jottie.app.usecase.MapRoomUseCase
import com.jottie.message.usecase.DeleteRoomUseCase
import com.jottie.message.usecase.RenameRoomUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

internal class MainViewModel(
    private val createRoomUseCase: CreateRoomUseCase,
    private val renameRoomUseCase: RenameRoomUseCase,
    private val deleteRoomUseCase: DeleteRoomUseCase,
    private val roomRepository: RoomRepository,
    private val mapRoomUseCase: MapRoomUseCase,
    private val soundEffectsPlayer: SoundEffectsPlayer
) : ViewModel() {

    val event = Channel<MainEvent>(UNLIMITED)

    private val _rooms = roomRepository.observeRooms()
    val rooms = _rooms.map { it.map(mapRoomUseCase::invoke) }

    val hasRooms = _rooms.map { it.isNotEmpty() }

    private val selectedRoomId = MutableStateFlow<Long?>(null)

    val currentRoomId = combine(
        flow = _rooms,
        flow2 = selectedRoomId,
        transform = { rooms, selected -> selected ?: rooms.firstOrNull()?.id }
    )

    fun setSelectedRoom(roomId: Long) {
        selectedRoomId.tryEmit(roomId)
    }

    fun createNewRoom() {
        viewModelScope.launch {
            createRoomUseCase()
                .onSuccess {
                    soundEffectsPlayer.playSound(SoundEffect.SoundEffectCreation)
                }
                .onFailure { /* Handle Error */ }
        }
    }

    fun deleteRoom(roomId: Long) {
        viewModelScope.launch {
            deleteRoomUseCase(roomId)
                .onSuccess {
                    soundEffectsPlayer.playSound(SoundEffect.SoundEffectRemoval)
                    if (roomId == selectedRoomId.value) {
                        selectedRoomId.emit(null)
                    }
                    event.send(MainEvent.OnRoomDeleted)
                }
                .onFailure { /* handle */ }

        }
    }

    fun renameRoom(roomId: Long, newName: String) {
        viewModelScope.launch {
            renameRoomUseCase(roomId, newName)
            event.send(MainEvent.OnRoomRenamed)
        }
    }

}