package com.jottie.message.data

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.jottie.message.data.join.MediaJoin
import com.jottie.message.data.join.LinkJoin

data class FullNote(
    @Embedded val note: NoteDto,
    @Relation(
        parentColumn = "noteId",
        entityColumn = "mediaId",
        associateBy = Junction(MediaJoin::class)
    )
    val media: List<MediaDto>?,
    @Relation(
        parentColumn = "noteId",
        entityColumn = "linkId",
        associateBy = Junction(LinkJoin::class)
    )
    val links: List<LinkDto>?
)