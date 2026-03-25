package com.jotte.message.persistence.converter

import androidx.room.TypeConverter
import com.jotte.message.data.WhiteboardDto
import kotlinx.serialization.json.Json

internal object PathConverter {

    private val json = Json { ignoreUnknownKeys = true }

    @TypeConverter
    fun from(paths: List<WhiteboardDto.Path>): String = json.encodeToString(paths)

    @TypeConverter
    fun to(string: String): List<WhiteboardDto.Path> = json.decodeFromString(string)

}