package com.jotte.message.repository

import com.jotte.message.data.RoomDto
import kotlinx.coroutines.flow.Flow
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

interface RoomRepository {

    fun observeRooms(): Flow<List<RoomDto>>
    fun observeRoom(roomId: Long): Flow<RoomDto?>

    suspend fun queryRoom(id: Long): RoomDto

    suspend fun insertRoom(room: RoomDto): Long

    suspend fun updateRoomName(id: Long, name: String): Int

    @OptIn(ExperimentalTime::class)
    suspend fun updateRoomModified(
        id: Long,
        modifiedOn: Long = Clock.System.now().toEpochMilliseconds()
    ): Int

    suspend fun deleteRoom(room: RoomDto)
}