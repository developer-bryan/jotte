package com.jottie.app.screen.layout

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.jottie.cxui.theme.sizes
import com.jottie.app.model.state.RoomState
import com.jottie.app.screen.component.DrawerListItem
import com.jottie.cxui.Res
import com.jottie.cxui.component.CXIcon
import com.jottie.cxui.component.CXText
import com.jottie.cxui.create_new_room
import com.jottie.cxui.icon_new_room
import com.jottie.cxui.new_room
import com.jottie.cxui.theme.typography
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun DrawerList(
    rooms: List<RoomState>,
    selectedRoomId: Long?,
    modifier: Modifier = Modifier,
    onRoomSelected: (roomId: Long) -> Unit,
    onRenameRoomClicked: (roomId: Long, currentName: String) -> Unit,
    onDeleteRoomClicked: (roomId: Long) -> Unit,
    onNewRoomClicked: () -> Unit
) {

    LazyColumn(
        modifier = modifier,
        state = rememberLazyListState(),
        verticalArrangement = Arrangement.spacedBy(sizes.tiny),
        content = {

            item {
                Spacer(Modifier.height(sizes.small))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(sizes.interactableHeight)
                        .clickable(
                            onClickLabel = stringResource(Res.string.create_new_room),
                            onClick = onNewRoomClicked
                        )
                        .padding(horizontal = sizes.regular),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(sizes.extraSmall),
                    content = {
                        CXIcon(Res.drawable.icon_new_room)
                        CXText(
                            text = stringResource(Res.string.new_room),
                            style = typography.headerTwo
                        )
                    }
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