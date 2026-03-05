@file:OptIn(ExperimentalUuidApi::class, ExperimentalTime::class)

package com.jotte.message.data

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Entity(
    tableName = "links",
    indices = [
        Index("linkId"),
        Index("linkType")
    ],
)
data class LinkDto(
    @PrimaryKey val linkId: String = Uuid.random().toString(),
    val createdOn: Long = Clock.System.now().toEpochMilliseconds(),
    val link: String,
    val linkType: LinkType
) {
    enum class LinkType { Url, Email, Phone }
}