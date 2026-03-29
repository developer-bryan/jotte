package com.jotte.data.persistence.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import com.jotte.data.persistence.data.MediaDto
import com.jotte.data.persistence.data.join.MediaJoin

@Dao
interface MediaDao {

    @Query("SELECT * FROM media WHERE mediaId = :fileId")
    suspend fun queryMedia(fileId: String): MediaDto?

    @Query("SELECT * FROM media_join WHERE mediaId = :fileId")
    suspend fun queryMediaJoin(fileId: String): MediaJoin?

    @Delete
    suspend fun deleteMedia(file: MediaDto): Int

    @Transaction
    suspend fun deleteMedia(files: List<MediaDto>): Int =
        files.fold(
            initial = 0,
            operation = { acc, value -> acc + deleteMedia(value) }
        )

}