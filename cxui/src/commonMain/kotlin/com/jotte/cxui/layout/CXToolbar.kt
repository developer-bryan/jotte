package com.jotte.cxui.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jotte.cxui.theme.colors
import com.jotte.cxui.theme.sizes

@Composable
internal fun ToolbarLayout(
    modifier: Modifier = Modifier,
    background: Color = colors.backgroundPrimary,
    bottomBorderWidth: Dp = 1.dp,
    leftIconContent: @Composable () -> Unit,
    screenHeaderContent: @Composable () -> Unit,
    trailingContent: @Composable () -> Unit
) {

    val padding = PaddingValues(
        horizontal = sizes.small,
        vertical = sizes.small
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = sizes.toolbarHeight)
            .background(background)
            .padding(padding)
            .then(modifier),
        content = {
            Box(
                modifier = Modifier.align(Alignment.CenterStart),
                content = { leftIconContent() }
            )

            Box(
                modifier = Modifier.align(Alignment.Center),
                content = { screenHeaderContent() }
            )

            Box(
                modifier = Modifier.align(Alignment.CenterEnd),
                content = { trailingContent() }
            )
        }
    )
}