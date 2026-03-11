package com.jotte.message.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jotte.message.data.WhiteboardDto

@Dao
interface WhiteboardDao {

    @Query("SELECT * FROM WHITEBOARD_DTO WHERE id = 1")
    suspend fun queryWhiteboard(): WhiteboardDto

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateWhiteboard(whiteboardDto: WhiteboardDto): Long

}