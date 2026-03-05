@file:OptIn(ExperimentalTime::class)

package com.jotte.message.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@Entity(tableName = "rooms")
data class RoomDto(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val createdOn: Long = Clock.System.now().toEpochMilliseconds(),
    val modifiedOn: Long = Clock.System.now().toEpochMilliseconds(),
    val name: String
)