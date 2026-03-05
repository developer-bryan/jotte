package com.jotte.app.screen

import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import com.jotte.app.model.event.MainEvent
import com.jotte.app.screen.dialog.RenameRoomDialog
import com.jotte.app.viewmodel.MainViewModel
import com.jotte.cxui.composition.LocalColor
import com.jotte.cxui.extension.asEffect
import com.jotte.cxui.scaffold.CXDrawerScaffold
import com.jotte.cxui.Res
import com.jotte.cxui.color.CXDarkColors
import com.jotte.cxui.composition.LocalToastController
import com.jotte.cxui.controller.rememberDialogController
import com.jotte.cxui.delete_room_dialog_body
import com.jotte.cxui.delete_room_dialog_title
import com.jotte.cxui.room_deleted
import com.jotte.cxui.room_renamed
import com.jotte.cxui.scaffold.CLOSED
import com.jotte.cxui.scaffold.OPEN
import com.jotte.cxui.scaffold.rememberDrawerState
import com.jotte.room.screen.RoomScreen
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@Composable
internal fun MainScreen(
    viewModel: MainViewModel,
    onAudioClicked: (audioId: String) -> Unit,
    onEditorClicked: (roomId: Long, noteId: Long?) -> Unit
) {

    val scope = rememberCoroutineScope()

    val toastState = LocalToastController.current

    val draggableState = rememberDrawerState()

    val rooms by viewModel.rooms.collectAsState(emptyList())
    val selectedRoomId by viewModel.currentRoomId.collectAsState(null)

    viewModel.event.receiveAsFlow().asEffect { event ->
        when (event) {
            MainEvent.OnRoomDeleted -> toastState.show(Res.string.room_deleted)
            MainEvent.OnRoomRenamed -> toastState.show(Res.string.room_renamed)
        }
    }

    val deleteRoomDialog = rememberDialogController<Long?>(
        title = Res.string.delete_room_dialog_title,
        body = Res.string.delete_room_dialog_body,
        onPositiveButtonClick = {
            it?.let { viewModel.deleteRoom(it) }
        }
    )

    val renameRoomDialogController = rememberDialogController<Pair<Long, String>> { req ->
        val (id, name) = req ?: return@rememberDialogController
        RenameRoomDialog(
            name = name,
            onNameEdited = {
                viewModel.renameRoom(id, it)
                this.hide()
            }
        )
    }

    Box {
        CXDrawerScaffold(
            draggableState = draggableState,
            drawerContent = {
                CompositionLocalProvider(LocalColor provides CXDarkColors()) {
                    DrawerScreen(
                        rooms = rooms,
                        selectedRoomId = selectedRoomId,
                        onNewRoomClicked = viewModel::createNewRoom,
                        onRoomSelected = {
                            viewModel.setSelectedRoom(it)
                            scope.launch { draggableState.animateTo(CLOSED) }
                        },
                        onDeleteRoom = deleteRoomDialog::show,
                        onRenameRoom = { id, name -> renameRoomDialogController.show(id to name) }
                    )
                }
            },
            bodyContent = {
                selectedRoomId?.let { roomId ->
                    RoomScreen(
                        roomId = roomId,
                        onDrawerClicked = { scope.launch { draggableState.animateTo(OPEN) } },
                        onAudioClicked = onAudioClicked,
                        onEditorClicked = { noteId -> onEditorClicked(roomId, noteId) },
                        onRenameRoomClicked = {
                            rooms.find { it.id == roomId }?.let {
                                renameRoomDialogController.show(roomId to it.name)
                            }
                        },
                        onDeleteRoomClicked = { deleteRoomDialog.show(roomId) }
                    )
                }
            }
        )
    }
}
