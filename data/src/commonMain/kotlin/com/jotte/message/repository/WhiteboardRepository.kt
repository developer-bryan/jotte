package com.jotte.message.repository

import com.jotte.message.data.WhiteboardDto

interface WhiteboardRepository {

    suspend fun queryWhiteboard(): WhiteboardDto?

    suspend fun updateWhiteboard(whiteboardDto: WhiteboardDto): Long

}