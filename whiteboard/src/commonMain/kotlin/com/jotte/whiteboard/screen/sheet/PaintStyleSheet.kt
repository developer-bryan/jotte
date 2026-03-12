package com.jotte.whiteboard.screen.sheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.jotte.whiteboard.model.data.PaintColor
import com.jotte.whiteboard.screen.layout.PaintColorSelector

@Composable
internal fun PaintStyleSheet(
    modifier: Modifier = Modifier,
    onColorSelected: (color: PaintColor) -> Unit
) {

    val colors = remember { PaintColor.toList() }

    Column(
        modifier = modifier.fillMaxWidth(),
        content = {

            PaintColorSelector(
                colors = colors,
                onColorSelected = onColorSelected
            )
        }
    )

}