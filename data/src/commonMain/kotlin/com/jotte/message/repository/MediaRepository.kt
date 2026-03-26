package com.jotte.message.repository

import com.jotte.message.data.MediaDto
import com.jotte.message.data.join.MediaJoin

interface MediaRepository {

    suspend fun queryMedia(mediaId: String): MediaDto?

    suspend fun queryMediaJoin(mediaId: String): MediaJoin?

    suspend fun deleteMedia(media: MediaDto): Int

    suspend fun deleteMedia(media: List<MediaDto>): Int

}