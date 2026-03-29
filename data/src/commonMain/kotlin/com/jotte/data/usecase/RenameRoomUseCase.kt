package com.jotte.data.usecase

import com.jotte.data.repository.RoomRepository

class RenameRoomUseCase(private val repository: RoomRepository) {

    suspend operator fun invoke(
        roomId: Long,
        name: String
    ) {
        repository.updateRoomName(roomId, name)
        repository.updateRoomModified(roomId)
    }

}