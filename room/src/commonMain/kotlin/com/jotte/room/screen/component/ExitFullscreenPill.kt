package com.jotte.room.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.jotte.cxui.Res
import com.jotte.cxui.component.CXIcon
import com.jotte.cxui.component.CXText
import com.jotte.cxui.exit_fullscreen
import com.jotte.cxui.icon_collapse
import com.jotte.cxui.theme.colors
import com.jotte.cxui.theme.shapes
import com.jotte.cxui.theme.sizes
import com.jotte.cxui.theme.typography
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun ExitFullscreenPill(
    modifier: Modifier = Modifier,
    onCloseFullScreenClicked: () -> Unit
) {

    Row(
        modifier =
            modifier
                .clip(shapes.roundedButtonShape)
                .background(color = colors.contentPrimary.copy(alpha = 0.25F))
                .clickable(onClick = onCloseFullScreenClicked)
                .padding(sizes.small),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(sizes.extraSmall),
    ) {
        CXIcon(icon = Res.drawable.icon_collapse)
        CXText(
            text = stringResource(Res.string.exit_fullscreen),
            style = typography.bodyOne
        )
    }

}