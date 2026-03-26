package com.jotte.message.usecase

import com.jotte.message.data.RoomDto
import com.jotte.message.repository.RoomRepository

class CreateRoomUseCase(private val repository: RoomRepository) {

    suspend operator fun invoke(name: String = "my room"): Result<Boolean> =
        runCatching {
            val defaultRoom = RoomDto(name = name)
            repository.insertRoom(defaultRoom)
        }.mapCatching {
            check((it != -1L))
            true
        }

}