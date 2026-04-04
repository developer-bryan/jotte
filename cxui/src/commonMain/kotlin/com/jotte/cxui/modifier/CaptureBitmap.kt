package com.jotte.cxui.modifier

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.layer.GraphicsLayer
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.toIntSize

fun Modifier.captureBitmap(
    graphicsLayer: GraphicsLayer,
    sizeProvider: ((DrawScope) -> IntSize)? = null
) = this
    .clipToBounds()
    .drawWithContent {
        graphicsLayer.record(
            size = sizeProvider?.invoke(this) ?: this.size.toIntSize(),
            block = { this@drawWithContent.drawContent() }
        )
        this@drawWithContent.drawLayer(graphicsLayer)
    }