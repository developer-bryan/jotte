package com.jotte.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jotte.app.model.event.MainEvent
import com.jotte.app.usecase.MapRoomUseCase
import com.jotte.cxui.soundeffect.SoundEffect
import com.jotte.cxui.soundeffect.SoundEffectsPlayer
import com.jotte.data.repository.RoomRepository
import com.jotte.data.usecase.CreateRoomUseCase
import com.jotte.data.usecase.DeleteRoomUseCase
import com.jotte.data.usecase.RenameRoomUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
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
    private val selectedRoomId = MutableStateFlow<Long?>(null)

    val hasRooms =
        _rooms
            .map { it.isNotEmpty() }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = null
            )

    val currentRoomId =
        combine(
            flow = _rooms,
            flow2 = selectedRoomId,
            transform = { rooms, selected -> selected ?: rooms.firstOrNull()?.id }
        ).stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = null
        )

    fun setSelectedRoom(roomId: Long) {
        selectedRoomId.tryEmit(roomId)
    }

    fun createNewRoom() {
        viewModelScope.launch {
            createRoomUseCase()
                .onSuccess {
                    soundEffectsPlayer.playSound(SoundEffect.SoundEffectCreation)
                }.onFailure { /* Handle Error */ }
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
                }.onFailure { /* handle */ }

        }
    }

    fun renameRoom(
        roomId: Long,
        newName: String
    ) {
        viewModelScope.launch {
            renameRoomUseCase(roomId, newName)
            event.send(MainEvent.OnRoomRenamed)
        }
    }

}