package com.jotte.audioplayer.screen.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jotte.cxui.Res
import com.jotte.cxui.component.CXButtonIcon
import com.jotte.cxui.icon_pause
import com.jotte.cxui.icon_play
import com.jotte.cxui.theme.colors
import com.jotte.cxui.theme.sizes

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