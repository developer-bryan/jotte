package com.jotte.camera.screen.component

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun FocusRing(
    tapOffset: Offset?,
    modifier: Modifier = Modifier,
    ringSize: Dp = 62.dp,
) {

    var visible by remember { mutableStateOf(false) }

    val alpha by animateFloatAsState(
        targetValue = if (visible && tapOffset != null) 1f else 0f,
        animationSpec = tween(
            durationMillis = 250,
            easing = LinearOutSlowInEasing
        )
    )

    LaunchedEffect(tapOffset) {
        if (tapOffset != null) {
            visible = true
            delay(650L)
            visible = false
        }
    }

    if (tapOffset == null) return

    Box(
        modifier = modifier
            .graphicsLayer {
                translationX = tapOffset.x - (ringSize.toPx() / 2)
                translationY = tapOffset.y - (ringSize.toPx() / 2)
                this.alpha = alpha
            }
            .size(ringSize)
            .border(
                width = 2.dp,
                color = Color.White.copy(alpha = alpha),
                shape = CircleShape
            )
    )
}