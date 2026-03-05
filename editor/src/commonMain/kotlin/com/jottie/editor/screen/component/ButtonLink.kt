package com.jottie.editor.screen.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jottie.cxui.Res
import com.jottie.cxui.component.CXButtonIcon
import com.jottie.cxui.icon_link
import com.jottie.cxui.theme.colors

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