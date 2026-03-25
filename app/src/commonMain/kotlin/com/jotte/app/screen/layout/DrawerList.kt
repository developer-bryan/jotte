package com.jotte.app.screen.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jotte.app.model.state.RoomState
import com.jotte.app.screen.component.DrawerListItem
import com.jotte.cxui.Res
import com.jotte.cxui.create_new_room
import com.jotte.cxui.icon_draw
import com.jotte.cxui.icon_new_room
import com.jotte.cxui.new_room
import com.jotte.cxui.open_whiteboard
import com.jotte.cxui.theme.sizes
import com.jotte.cxui.whiteboard

@Composable
internal fun DrawerList(
    rooms: List<RoomState>,
    selectedRoomId: Long?,
    modifier: Modifier = Modifier,
    onRoomSelected: (roomId: Long) -> Unit,
    onRenameRoomClicked: (roomId: Long, currentName: String) -> Unit,
    onDeleteRoomClicked: (roomId: Long) -> Unit,
    onNewRoomClicked: () -> Unit,
    onWhiteboardClicked: () -> Unit
) {

    LazyColumn(
        modifier = modifier,
        state = rememberLazyListState(),
        verticalArrangement = Arrangement.spacedBy(sizes.tiny),
        content = {

            item {
                Spacer(Modifier.height(sizes.small))
                DrawerOptionItem(
                    iconResource = Res.drawable.icon_new_room,
                    labelResource = Res.string.new_room,
                    onClickLabelResource = Res.string.create_new_room,
                    onClick = onNewRoomClicked
                )
            }

            item {
                DrawerOptionItem(
                    iconResource = Res.drawable.icon_draw,
                    labelResource = Res.string.whiteboard,
                    onClickLabelResource = Res.string.open_whiteboard,
                    onClick = onWhiteboardClicked
                )
            }

            itemsIndexed(
                items = rooms,
                key = { _, room -> room.id },
                itemContent = { index, room ->
                    if (index == 0) {
                        Spacer(Modifier.height(sizes.small))
                    }
                    DrawerListItem(
                        roomName = room.name,
                        modifiedOn = room.modifiedOn,
                        isSelected = room.id == selectedRoomId,
                        onClick = { onRoomSelected(room.id) },
                        onRenameClicked = { onRenameRoomClicked(room.id, room.name) },
                        onDeleteClicked = { onDeleteRoomClicked(room.id) }
                    )
                    if (index == rooms.lastIndex) {
                        Spacer(Modifier.height(sizes.huge))
                    }
                }
            )
        }
    )
}