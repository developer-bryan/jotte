package com.jotte.whiteboard.usecase

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jotte.message.data.WhiteboardDto
import com.jotte.whiteboard.model.data.PaintColor
import com.jotte.whiteboard.model.state.WhiteboardPath

internal class MapWhiteboardPathUseCase {

    operator fun invoke(path: WhiteboardDto.Path): WhiteboardPath {

        val points = path.offsets.map { Offset(it.x, it.y) }
        val color = PaintColor.fromColor(Color(path.color))
        val size = path.width.dp

        return WhiteboardPath(
            points = points,
            color = color,
            size = size
        )
    }

}