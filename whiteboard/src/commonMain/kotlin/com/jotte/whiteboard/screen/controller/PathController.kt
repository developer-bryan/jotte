package com.jotte.whiteboard.screen.controller

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jotte.whiteboard.model.data.PaintColor
import com.jotte.whiteboard.model.state.WhiteboardPath

internal class PathController(private val onPaintEnd: (WhiteboardPath) -> Unit) {

    var activeOffsets = mutableStateListOf<Offset>()
    var activeColor by mutableStateOf<PaintColor>(PaintColor.Black)
    var activeSize: Dp by mutableStateOf(6.dp)

    fun beginPaint(startingPoint: Offset) {
        activeOffsets.clear()
        activeOffsets.add(startingPoint)
    }

    fun updatePaint(nextPoint: Offset) {
        activeOffsets.add(nextPoint)
    }

    fun endPaint() {
        val whiteboardPath =
            WhiteboardPath(
                points = activeOffsets.toList(),
                size = activeSize,
                color = activeColor
            )

        onPaintEnd(whiteboardPath)
        activeOffsets.clear()
    }

    fun cancelPaint() {
        activeOffsets.clear()
    }

    fun updatePaintColor(color: PaintColor) {
        this.activeColor = color
    }

}

@Composable
internal fun rememberPathController(onPaintEnd: (WhiteboardPath) -> Unit): PathController =
    remember { PathController(onPaintEnd) }