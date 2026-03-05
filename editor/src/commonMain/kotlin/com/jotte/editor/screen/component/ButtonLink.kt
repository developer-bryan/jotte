package com.jotte.editor.screen.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jotte.cxui.Res
import com.jotte.cxui.component.CXButtonIcon
import com.jotte.cxui.icon_link
import com.jotte.cxui.theme.colors

@Composable
internal fun ButtonLink(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    CXButtonIcon(
        modifier = modifier,
        icon = Res.drawable.icon_link,
        backgroundColor = colors.contentPrimary,
        iconColor = colors.contentPrimaryInverse,
        onClick = onClick
    )
}