package com.jotte.message.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "whiteboard_dto")
data class WhiteboardDto(
    @PrimaryKey val id: Long = 1L,
    val paths: List<Path>
) {

    @Serializable
    data class Path(
        val offsets: List<PathOffset>,
        val color: Int,
        val width: Float
    )

    @Serializable
    data class PathOffset(
        val x: Float,
        val y: Float
    )

}