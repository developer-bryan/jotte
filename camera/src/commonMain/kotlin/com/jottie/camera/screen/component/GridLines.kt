package com.jottie.camera.screen.component

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jottie.cxui.color.Pallete.White

private data class Point(val x: Int, val y: Int)

@Composable
internal fun GridLines(
    modifier: Modifier = Modifier,
    lineWidth: Dp = 1.dp,
    lineColor: Color = White.copy(alpha = 0.50F)
) {

    Canvas(
        modifier = modifier,
        onDraw = {

            val cellWidth = size.width / 3
            val cellHeight = size.height / 3

            repeat(2) { iter ->
                val xOffset = cellWidth * (iter + 1)
                val yOffset = cellHeight * (iter + 1)

                // vertical
                drawLine(
                    color = lineColor,
                    strokeWidth = lineWidth.toPx(),
                    start = Offset(xOffset, 0F),
                    end = Offset(xOffset, size.height),
                )

                // horizontal
                drawLine(
                    color = lineColor,
                    strokeWidth = lineWidth.toPx(),
                    start = Offset(0F, yOffset),
                    end = Offset(size.width, yOffset),
                )
            }

        }
    )

}