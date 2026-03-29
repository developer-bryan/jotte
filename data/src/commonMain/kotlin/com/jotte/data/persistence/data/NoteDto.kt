@file:OptIn(ExperimentalTime::class, ExperimentalUuidApi::class)

package com.jotte.data.persistence.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlin.time.ExperimentalTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Entity(
    tableName = "notes",
    indices = [Index("roomId"), Index("noteId")]
)
data class NoteDto(
    @PrimaryKey(autoGenerate = true)
    val noteId: Long = 0L,
    val roomId: Long,
    val createdOn: Long,
    val modifiedOn: Long, // TODO: Remove
    @Embedded(prefix = "content_") val content: Content? = null,
    @Embedded(prefix = "audio_") val audio: Audio? = null,
) {

    data class Content(val value: String)

    data class Audio(
        val audioId: String = Uuid.random().toString(),
        val fileName: String,
        val duration: Long,
        val title: String? = null
    )

}