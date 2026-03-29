package com.jotte.data.persistence.data.join

import androidx.room.Entity
import androidx.room.ForeignKey
import com.jotte.data.persistence.data.MediaDto
import com.jotte.data.persistence.data.NoteDto

@Entity(
    tableName = "media_join",
    primaryKeys = ["noteId", "mediaId"],
    foreignKeys = [
        ForeignKey(
            entity = NoteDto::class,
            parentColumns = ["noteId"],
            childColumns = ["noteId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = MediaDto::class,
            parentColumns = ["mediaId"],
            childColumns = ["mediaId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class MediaJoin(
    val noteId: Long,
    val mediaId: String
)