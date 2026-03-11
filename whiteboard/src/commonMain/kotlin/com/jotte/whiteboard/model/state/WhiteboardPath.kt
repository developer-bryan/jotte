package com.jotte.whiteboard.model.state

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

internal data class WhiteboardPath(
    val points: List<Offset>,
    val size: Dp,
    val color: Color,
)