package com.jotte.data.repository

import com.jotte.data.persistence.dao.WhiteboardDao
import com.jotte.data.persistence.data.WhiteboardDto

class WhiteboardRepositoryImpl(private val dao: WhiteboardDao) : WhiteboardRepository {

    override suspend fun queryWhiteboard(): WhiteboardDto? = dao.queryWhiteboard()

    override suspend fun updateWhiteboard(whiteboardDto: WhiteboardDto): Long = dao.updateWhiteboard(whiteboardDto)
}