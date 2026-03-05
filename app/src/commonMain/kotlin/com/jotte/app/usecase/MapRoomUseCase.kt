package com.jotte.app.usecase

import com.jotte.app.model.state.RoomState
import com.jotte.core.datetime.usecase.FullDate
import com.jotte.core.datetime.usecase.GetFullDateUseCase
import com.jotte.message.data.RoomDto

class MapRoomUseCase(private val getFullDateUseCase: GetFullDateUseCase) {

    operator fun invoke(room: RoomDto): RoomState {

        val modifiedOn = getFullDateUseCase(room.modifiedOn)
        val createdOn = getFullDateUseCase(room.createdOn)

        return RoomState(
            id = room.id,
            name = room.name,
            modifiedOn = modifiedOn.asFullString(),
            createdOn = createdOn.asFullString()
        )
    }

    private fun FullDate.asFullString(delimiter: String = "@"): String = "$date $delimiter $time"

}