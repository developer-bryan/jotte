package com.jotte.data.persistence.data.join

import androidx.room.Entity
import androidx.room.ForeignKey
import com.jotte.data.persistence.data.LinkDto
import com.jotte.data.persistence.data.NoteDto

@Entity(
    tableName = "note_link_join",
    primaryKeys = ["noteId", "linkId"],
    foreignKeys = [
        ForeignKey(
            entity = NoteDto::class,
            parentColumns = ["noteId"],
            childColumns = ["noteId"],
            onDelete = ForeignKey.Companion.CASCADE
        ),
        ForeignKey(
            entity = LinkDto::class,
            parentColumns = ["linkId"],
            childColumns = ["linkId"],
            onDelete = ForeignKey.Companion.CASCADE
        )
    ]
)
data class LinkJoin(
    val noteId: Long,
    val linkId: String
)