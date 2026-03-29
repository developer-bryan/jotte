package com.jotte.data.repository

import com.jotte.data.persistence.data.WhiteboardDto

interface WhiteboardRepository {

    suspend fun queryWhiteboard(): WhiteboardDto?

    suspend fun updateWhiteboard(whiteboardDto: WhiteboardDto): Long

}