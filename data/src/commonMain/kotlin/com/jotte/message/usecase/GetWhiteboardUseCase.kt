package com.jotte.message.usecase

import com.jotte.message.data.WhiteboardDto
import com.jotte.message.repository.WhiteboardRepository

class GetWhiteboardUseCase(private val repository: WhiteboardRepository) {

    suspend operator fun invoke(): WhiteboardDto {
        return repository.queryWhiteboard()
    }

}