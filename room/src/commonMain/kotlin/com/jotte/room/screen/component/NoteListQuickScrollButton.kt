package com.jotte.room.screen.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jotte.cxui.Res
import com.jotte.cxui.component.CXButtonIcon
import com.jotte.cxui.icon_arrow_down
import com.jotte.cxui.theme.colors

@Composable
internal fun NoteListQuickScrollButton(
    isVisible: Boolean = false,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    AnimatedVisibility(
        visible = isVisible,
        modifier = modifier,
        enter = slideInVertically(
            animationSpec = spring(Spring.DampingRatioMediumBouncy),
            initialOffsetY = { it }
        ),
        exit = slideOutVertically(
            animationSpec = tween(
                durationMillis = 250,
                easing = LinearOutSlowInEasing
            ),
            targetOffsetY = { it }
        ),
        content = {
            CXButtonIcon(
                icon = Res.drawable.icon_arrow_down,
                backgroundColor = colors.contentPrimary,
                iconColor = colors.backgroundPrimary,
                modifier = Modifier,
                onClick = onClick
            )
        }
    )
}