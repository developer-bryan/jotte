package com.jottie.audioplayer.screen.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jottie.cxui.Res
import com.jottie.cxui.component.CXButtonIcon
import com.jottie.cxui.icon_pause
import com.jottie.cxui.icon_play
import com.jottie.cxui.theme.colors
import com.jottie.cxui.theme.sizes

@Composable
internal fun AudioShutterButton(
    isPlaying: Boolean = false,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    CXButtonIcon(
        icon = if (isPlaying) Res.drawable.icon_pause else Res.drawable.icon_play,
        modifier = modifier,
        size = sizes.interactableHeight,
        backgroundColor = colors.accentColor,
        iconColor = colors.onAccentColor,
        onClick = onClick
    )

}