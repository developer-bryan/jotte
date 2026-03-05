package com.jotte.message.persistence.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jotte.message.data.RoomDto
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomDao {

    @Delete
    suspend fun deleteRoom(room: RoomDto)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRoom(room: RoomDto): Long

    @Query("SELECT * FROM rooms WHERE id = :id")
    suspend fun queryRoom(id: Long): RoomDto

    @Query("SELECT * FROM rooms ORDER BY modifiedOn DESC")
    fun observeRooms(): Flow<List<RoomDto>>

    @Query("SELECT * FROM rooms WHERE id = :roomId")
    fun observeRoom(roomId: Long): Flow<RoomDto?>

    @Query("UPDATE rooms SET name = :name WHERE id = :roomId")
    suspend fun updateRoomName(roomId: Long, name: String): Int

    @Query("UPDATE rooms SET modifiedOn = :modifiedOn WHERE id = :roomId")
    suspend fun updateRoomLastModified(roomId: Long, modifiedOn: Long): Int

}