package com.jotte.message.repository

import com.jotte.message.data.MediaDto
import com.jotte.message.data.join.MediaJoin
import com.jotte.message.persistence.dao.MediaDao

class MediaRepositoryImpl(private val dao: MediaDao): MediaRepository {

    override suspend fun queryMedia(mediaId: String): MediaDto? = dao.queryMedia(mediaId)
    override suspend fun queryMediaJoin(mediaId: String): MediaJoin? = dao.queryMediaJoin(mediaId)

    override suspend fun deleteMedia(media: MediaDto): Int = dao.deleteMedia(media)
    override suspend fun deleteMedia(media: List<MediaDto>): Int = dao.deleteMedia(media)
}