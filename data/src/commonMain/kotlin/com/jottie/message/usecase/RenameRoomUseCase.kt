package com.jottie.message.usecase

import com.jottie.message.repository.RoomRepository

class RenameRoomUseCase(private val repository: RoomRepository) {

    suspend operator fun invoke(roomId: Long, name: String) {
        repository.updateRoomName(roomId, name)
        repository.updateRoomModified(roomId)
    }

}