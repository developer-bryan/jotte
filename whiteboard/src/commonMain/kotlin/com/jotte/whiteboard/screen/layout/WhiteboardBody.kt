package com.jotte.whiteboard.screen.layout

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.layer.GraphicsLayer
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.platform.LocalDensity
import com.jotte.whiteboard.model.state.WhiteboardPath
import com.jotte.whiteboard.model.data.PaintedPath
import com.jotte.whiteboard.screen.controller.PathController
import com.jotte.whiteboard.screen.modifier.painterGestures

@Composable
internal fun WhiteboardBody(
    controller: PathController,
    graphicsLayer: GraphicsLayer,
    paths: List<WhiteboardPath>,
    modifier: Modifier = Modifier,
    canConsumePaintTouches: Boolean = true,
) {

    val density = LocalDensity.current

    val paintGestures = if (canConsumePaintTouches) {
        Modifier.painterGestures(controller)
    } else {
        Modifier
    }

    Box(
        modifier = modifier
            .clipToBounds()
            .then(paintGestures)
            .drawWithContent {
            graphicsLayer.record {
                this@drawWithContent.drawContent()
            }
            this@drawWithContent.drawLayer(graphicsLayer)
        },
        content = {
            Canvas(
                modifier = modifier.fillMaxSize(),
                onDraw = {
                    paths.forEach {
                        if (it.points.size == 1) {
                            drawCircle(
                                color = it.color.color,
                                radius = with(density) { it.size.toPx() } * 0.5F,
                                center = it.points.first()
                            )
                        } else {
                            drawPaintedPath(
                                points = it.points,
                                size = with(density) { it.size.toPx() },
                                color = it.color.color
                            )
                        }
                    }

                    if (controller.activeOffsets.isNotEmpty()) {
                        drawPaintedPath(
                            points = controller.activeOffsets,
                            size = controller.activeSize.toPx(),
                            color = controller.activeColor.color
                        )
                    }
                }
            )
        }
    )
}

private fun DrawScope.drawPaintedPath(
    points: List<Offset>,
    size: Float,
    color: Color
) {
    drawPath(
        path = PaintedPath(points).path,
        color = color,
        style = Stroke(
            width = size,
            cap = StrokeCap.Round,
            join = StrokeJoin.Round
        )
    )
}