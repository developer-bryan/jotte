package com.jotte.whiteboard.screen.modifier

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import com.jotte.whiteboard.screen.controller.PathController

internal fun Modifier.painterGestures(controller: PathController) = this.pointerInput(
    key1 = "tap",
    block = { detectTapGestures(onTap = { controller.beginPaint(it) }) }
).pointerInput(
    key1 = "drag",
    block = {
        detectDragGestures(
            onDragStart = controller::beginPaint,
            onDrag = { change, _ -> controller.updatePaint(change.position) },
            onDragEnd = controller::endPaint,
            onDragCancel = controller::cancelPaint
        )
    }
)