package com.jotte.data.persistence.data

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.jotte.data.persistence.data.join.MediaJoin

data class FullNote(
    @Embedded val note: NoteDto,
    @Relation(
        parentColumn = "noteId",
        entityColumn = "mediaId",
        associateBy = Junction(MediaJoin::class)
    )
    val media: List<MediaDto>?,
)