package com.jotte.whiteboard.usecase

import androidx.compose.ui.graphics.toArgb
import com.jotte.message.data.WhiteboardDto
import com.jotte.message.repository.WhiteboardRepository
import com.jotte.whiteboard.model.state.WhiteboardPath

internal class UpdateWhiteboardUseCase(private val repository: WhiteboardRepository) {

    suspend operator fun invoke(whiteboardPaths: List<WhiteboardPath>) {

        val paths = whiteboardPaths.map {
            val offsets = it.points.map { WhiteboardDto.PathOffset(it.x, it.y) }
            WhiteboardDto.Path(
                offsets = offsets,
                color = it.color.color.toArgb(),
                width = it.size.value
            )
        }

        val whiteboard = WhiteboardDto(paths = paths)

        repository.updateWhiteboard(whiteboard)

    }

}