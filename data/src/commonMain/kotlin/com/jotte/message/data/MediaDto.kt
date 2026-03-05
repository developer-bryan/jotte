@file:OptIn(ExperimentalTime::class, ExperimentalUuidApi::class)

package com.jotte.message.data

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Entity(
    tableName = "media",
    indices = [Index("mediaId")],
)
data class MediaDto(
    @PrimaryKey  val mediaId: String = Uuid.random().toString(),
    val createdOn: Long = Clock.System.now().toEpochMilliseconds(),
    val fileName: String,
)