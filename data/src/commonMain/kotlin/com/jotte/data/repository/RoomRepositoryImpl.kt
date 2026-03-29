package com.jotte.data.repository

import com.jotte.data.persistence.dao.RoomDao
import com.jotte.data.persistence.data.RoomDto
import kotlinx.coroutines.flow.Flow
import kotlin.time.ExperimentalTime

internal class RoomRepositoryImpl(private val dao: RoomDao) : RoomRepository {

    override fun observeRooms(): Flow<List<RoomDto>> = dao.observeRooms()

    override fun observeRoom(roomId: Long): Flow<RoomDto?> = dao.observeRoom(roomId)

    override suspend fun queryRoom(id: Long): RoomDto = dao.queryRoom(id)

    override suspend fun insertRoom(room: RoomDto): Long = dao.insertRoom(room)

    @OptIn(ExperimentalTime::class)
    override suspend fun updateRoomName(
        id: Long,
        name: String
    ): Int = dao.updateRoomName(id, name)

    override suspend fun updateRoomModified(
        id: Long,
        modifiedOn: Long
    ): Int = dao.updateRoomLastModified(id, modifiedOn)

    override suspend fun deleteRoom(room: RoomDto) = dao.deleteRoom(room)
}