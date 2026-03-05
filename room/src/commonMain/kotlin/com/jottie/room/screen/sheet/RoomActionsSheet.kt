package com.jottie.room.screen.sheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jottie.cxui.Res
import com.jottie.cxui.component.CXButtonOption
import com.jottie.cxui.delete_room
import com.jottie.cxui.icon_expand
import com.jottie.cxui.icon_metrics
import com.jottie.cxui.icon_remove
import com.jottie.cxui.icon_text
import com.jottie.cxui.icon_trash
import com.jottie.cxui.rename_room
import com.jottie.cxui.theme.colors
import com.jottie.cxui.theme.sizes
import com.jottie.cxui.view_fullscreen
import com.jottie.cxui.view_metrics
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