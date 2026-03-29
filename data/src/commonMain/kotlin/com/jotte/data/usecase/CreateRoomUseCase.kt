package com.jotte.data.usecase

import com.jotte.data.persistence.data.RoomDto
import com.jotte.data.repository.RoomRepository

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