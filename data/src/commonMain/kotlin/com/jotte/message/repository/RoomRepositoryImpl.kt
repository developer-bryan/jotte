package com.jotte.message.repository

import com.jotte.message.data.RoomDto
import com.jotte.message.persistence.dao.RoomDao
import kotlinx.coroutines.flow.Flow
import kotlin.time.ExperimentalTime

internal class RoomRepositoryImpl(private val dao: RoomDao) : RoomRepository {

    override fun observeRooms(): Flow<List<RoomDto>> = dao.observeRooms()
    override fun observeRoom(roomId: Long): Flow<RoomDto?> = dao.observeRoom(roomId)
    override suspend fun queryRoom(id: Long): RoomDto = dao.queryRoom(id)
    override suspend fun insertRoom(room: RoomDto): Long = dao.insertRoom(room)
    @OptIn(ExperimentalTime::class)
    override suspend fun updateRoomName(id: Long, name: String): Int {
        return dao.updateRoomName(id, name)
    }

    override suspend fun updateRoomModified(id: Long, modifiedOn: Long): Int =
        dao.updateRoomLastModified(id, modifiedOn)

    override suspend fun deleteRoom(room: RoomDto) = dao.deleteRoom(room)
}