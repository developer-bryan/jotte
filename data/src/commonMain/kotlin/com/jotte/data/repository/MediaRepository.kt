package com.jotte.data.repository

import com.jotte.data.persistence.data.MediaDto
import com.jotte.data.persistence.data.join.MediaJoin

interface MediaRepository {

    suspend fun queryMedia(mediaId: String): MediaDto?

    suspend fun queryMediaJoin(mediaId: String): MediaJoin?

    suspend fun deleteMedia(media: MediaDto): Int

    suspend fun deleteMedia(media: List<MediaDto>): Int

}