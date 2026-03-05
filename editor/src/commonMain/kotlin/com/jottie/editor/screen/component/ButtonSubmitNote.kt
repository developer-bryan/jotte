package com.jottie.editor.screen.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jottie.cxui.Res
import com.jottie.cxui.component.CXButtonIcon
import com.jottie.cxui.icon_arrow_up
import com.jottie.cxui.theme.colors
import com.jottie.cxui.theme.sizes

@Composable
internal fun ButtonSubmitNote(
    modifier: Modifier = Modifier,
    enabled: Boolean = false,
    onClick: () -> Unit
) {

    CXButtonIcon(
        modifier = modifier,
        icon = Res.drawable.icon_arrow_up,
        backgroundColor = if (enabled) colors.accentColor else colors.contentPrimary,
        iconColor = if (enabled) colors.onAccentColor else colors.contentPrimaryInverse,
        onClick = { if (enabled) onClick() }
    )
}