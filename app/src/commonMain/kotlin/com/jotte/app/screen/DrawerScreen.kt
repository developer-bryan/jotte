package com.jotte.app.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jotte.cxui.theme.CXTheme
import com.jotte.cxui.theme.colors
import com.jotte.cxui.theme.sizes
import com.jotte.app.model.state.RoomState
import com.jotte.app.screen.layout.DrawerList
import com.jotte.app.screen.layout.DrawerHeader
import com.jotte.cxui.component.CXText
import com.jotte.cxui.theme.typography

@Composable
internal fun DrawerScreen(
    modifier: Modifier = Modifier,
    rooms: List<RoomState>,
    selectedRoomId: Long?,
    onNewRoomClicked: () -> Unit,
    onRoomSelected: (roomId: Long) -> Unit,
    onDeleteRoom: (roomId: Long) -> Unit,
    onRenameRoom: (roomId: Long, name: String) -> Unit
) {

    Column(
        modifier = modifier
            .width(sizes.roomDrawerWidth)
            .fillMaxHeight()
            .background(colors.backgroundSecondary)
            .windowInsetsPadding(WindowInsets.navigationBars),
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {

            DrawerHeader {}

            DrawerList(
                rooms = rooms,
                selectedRoomId = selectedRoomId,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F),
                onRoomSelected = onRoomSelected,
                onRenameRoomClicked = onRenameRoom,
                onDeleteRoomClicked = onDeleteRoom,
                onNewRoomClicked = onNewRoomClicked
            )

            CXText(
                text = "v0.0.0", // TODO: Get from BuildKonfig
                style = typography.bodyOne,
                alpha = sizes.reducedAlpha
            )
        }
    )
}

@Preview
@Composable
private fun Preview() {
    CXTheme(isDarkMode = true) {
        val rooms = (0..15).map {
            RoomState(
                id = it.toLong(),
                name = "room $it",
                modifiedOn = "",
                createdOn = ""
            )
        }

        DrawerScreen(
            modifier = Modifier,
            rooms = rooms,
            selectedRoomId = rooms.first().id,
            onNewRoomClicked = {},
            onRoomSelected = {},
            onDeleteRoom = {},
            onRenameRoom = { _, _ -> }
        )
    }
}