package com.jotte.data.usecase

import com.jotte.data.persistence.data.WhiteboardDto
import com.jotte.data.repository.WhiteboardRepository

class GetWhiteboardUseCase(private val repository: WhiteboardRepository) {

    suspend operator fun invoke(): WhiteboardDto? = repository.queryWhiteboard()

}