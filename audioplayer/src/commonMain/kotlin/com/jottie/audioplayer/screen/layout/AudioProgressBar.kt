package com.jottie.audioplayer.screen.layout

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.jottie.core.datetime.toFormattedRuntime
import com.jottie.cxui.component.CXText
import com.jottie.cxui.theme.colors
import com.jottie.cxui.theme.sizes
import com.jottie.cxui.theme.typography

@Composable
internal fun AudioProgressBar(
    current: Long,
    duration: Long,
    modifier: Modifier = Modifier
) {

    val progressAnim = remember { Animatable(0F) }

    val progress by derivedStateOf {
        if (duration > 0) {
            current.toFloat() / duration.toFloat()
        } else {
            0f
        }
    }

    LaunchedEffect(progress) {
        progressAnim.animateTo(
            targetValue = progress,
            animationSpec = tween(durationMillis = 1000)
        )
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(sizes.extraTiny),
        content = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(sizes.tiny)
                    .background(colors.contentPrimary, CircleShape),
                contentAlignment = Alignment.CenterStart,
                content = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(progressAnim.value)
                            .fillMaxHeight()
                            .background(colors.accentColor, CircleShape)
                    )
                }
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                content = {
                    CXText(text = current.toFormattedRuntime(), style = typography.bodyTwo)
                    CXText(text = duration.toFormattedRuntime(), style = typography.bodyTwo)
                }
            )

        }
    )

}