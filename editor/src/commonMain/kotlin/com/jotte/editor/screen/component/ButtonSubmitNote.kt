package com.jotte.editor.screen.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jotte.cxui.Res
import com.jotte.cxui.component.CXButtonIcon
import com.jotte.cxui.icon_arrow_up
import com.jotte.cxui.theme.colors

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