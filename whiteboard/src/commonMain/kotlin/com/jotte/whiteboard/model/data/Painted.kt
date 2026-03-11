package com.jotte.whiteboard.model

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import kotlin.uuid.ExperimentalUuidApi

class Painted(
    val points: List<Offset>,
    val size: Dp,
    val color: Color,
)
