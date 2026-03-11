package com.lint.editor.painter.modifier

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import com.lint.editor.painter.model.PainterState

internal fun Modifier.painterGestures(
    state: PainterState,
    onPaintFinished: () -> Unit
) = this.pointerInput(
    key1 = "tap",
    block = { detectTapGestures(onTap = { state.beginPaint(it); onPaintFinished() }) }
).pointerInput(
    key1 = "drag",
    block = {
        detectDragGestures(
            onDragStart = state::beginPaint,
            onDrag = { change, _ -> state.updatePaint(change.position) },
            onDragEnd = onPaintFinished,
            onDragCancel = onPaintFinished
        )
    }
)

