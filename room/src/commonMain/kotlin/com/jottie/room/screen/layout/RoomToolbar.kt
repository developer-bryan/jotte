package com.jottie.room.screen.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.jottie.cxui.Res
import com.jottie.cxui.component.CXButtonIcon
import com.jottie.cxui.component.CXText
import com.jottie.cxui.icon_hamburger
import com.jottie.cxui.theme.colors
import com.jottie.cxui.theme.sizes
import com.jottie.cxui.theme.typography

@Composable
internal fun RoomToolbar(
    roomTitle: String,
    modifier: Modifier = Modifier,
    onDrawerClicked: () -> Unit,
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(colors.backgroundPrimary)
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(bottom = sizes.tiny)
            .padding(end = sizes.extraSmall),
        verticalAlignment = Alignment.CenterVertically,
        content = {
            CXButtonIcon(
                icon = Res.drawable.icon_hamburger,
                onClick = onDrawerClicked
            )
            CXText(
                text = roomTitle,
                style = typography.headerOne
            )
        }
    )

}