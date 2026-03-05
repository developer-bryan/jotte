package com.jottie.message.usecase

import com.jottie.message.data.RoomDto
import com.jottie.message.repository.RoomRepository

class CreateRoomUseCase(private val repository: RoomRepository) {

    suspend operator fun invoke(name: String = "my room"): Result<Boolean> {
        return runCatching {
            val defaultRoom = RoomDto(name = name)
            repository.insertRoom(defaultRoom)
        }.mapCatching {
            if(it != -1L) true else throw IllegalStateException("unable to create room")
        }
    }

}