package com.jotte.whiteboard.model

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path

internal class PaintedPath {

    val path: Path = Path()

    internal constructor(points: List<Offset>) {
        var previous: Offset = points.first()

        moveTo(previous)

        points.forEachIndexed { index, point ->
            val midPoint = calculateMidpoint(previous, point)
            quadraticTo(previous, midPoint)
            previous = point
        }

        lineTo(previous)
    }

    private fun moveTo(point: Offset) = path.moveTo(point.x, point.y)

    private fun quadraticTo(point1: Offset, point2: Offset) =
        path.quadraticTo(point1.x, point1.y, point2.x, point2.y)

    private fun lineTo(point: Offset) = path.lineTo(point.x, point.y)

    private fun calculateMidpoint(start: Offset, end: Offset): Offset {
        val xCoord = (start.x + end.x) * 0.5F
        val yCoord = (start.y + end.y) * 0.5F

        return Offset(xCoord, yCoord)
    }

}