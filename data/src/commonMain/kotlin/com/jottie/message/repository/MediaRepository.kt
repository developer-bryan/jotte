package com.jottie.message.repository

import com.jottie.message.data.MediaDto
import com.jottie.message.data.join.MediaJoin

interface MediaRepository {

    suspend fun queryMedia(mediaId: String): MediaDto?
    suspend fun queryMediaJoin(mediaId: String): MediaJoin?

    suspend fun deleteMedia(media: MediaDto): Int
    suspend fun deleteMedia(media: List<MediaDto>): Int

}