package com.jotte.message.repository

import com.jotte.message.data.WhiteboardDto
import com.jotte.message.persistence.dao.WhiteboardDao

class WhiteboardRepositoryImpl(private val dao: WhiteboardDao) : WhiteboardRepository {

    override suspend fun queryWhiteboard(): WhiteboardDto? = dao.queryWhiteboard()

    override suspend fun updateWhiteboard(whiteboardDto: WhiteboardDto): Long = dao.updateWhiteboard(whiteboardDto)
}