package com.jotte.room.screen.sheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jotte.cxui.Res
import com.jotte.cxui.component.CXButtonOption
import com.jotte.cxui.delete_room
import com.jotte.cxui.icon_expand
import com.jotte.cxui.icon_metrics
import com.jotte.cxui.icon_remove
import com.jotte.cxui.icon_text
import com.jotte.cxui.icon_trash
import com.jotte.cxui.rename_room
import com.jotte.cxui.theme.colors
import com.jotte.cxui.theme.sizes
import com.jotte.cxui.view_fullscreen
import com.jotte.cxui.view_metrics
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun RoomActionsSheet(
    modifier: Modifier = Modifier,
    onRenameRoomClicked: () -> Unit,
    onFullScreenClicked: () -> Unit,
    onViewMetricsClicked: () -> Unit,
    onDeleteClicked: () -> Unit
) {

    Column(
        modifier = modifier
            .windowInsetsPadding(WindowInsets.navigationBars)
            .padding(top = sizes.extraSmall),
        content = {

            CXButtonOption(
                label = stringResource(Res.string.view_fullscreen),
                icon = Res.drawable.icon_expand,
                onClick = onFullScreenClicked
            )

            CXButtonOption(
                label = stringResource(Res.string.rename_room),
                icon = Res.drawable.icon_text,
                onClick = onRenameRoomClicked
            )

            CXButtonOption(
                label = stringResource(Res.string.view_metrics),
                icon = Res.drawable.icon_metrics,
                onClick = onViewMetricsClicked
            )

            CXButtonOption(
                label = stringResource(Res.string.delete_room),
                icon = Res.drawable.icon_trash,
                contentTint = colors.negativeColor,
                onClick = onDeleteClicked
            )

        }
    )

}