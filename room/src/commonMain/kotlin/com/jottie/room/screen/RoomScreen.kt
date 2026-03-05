package com.jottie.room.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.jottie.cxui.Res
import com.jottie.cxui.component.CXMediaPager
import com.jottie.cxui.extension.asEffect
import com.jottie.cxui.default_room_name
import com.jottie.cxui.theme.shapes
import com.jottie.room.model.event.RoomEvent
import com.jottie.room.screen.component.ExitFullscreenPill
import com.jottie.room.screen.controller.rememberRoomScreenController
import com.jottie.room.screen.layout.NotesEmptyLayout
import com.jottie.room.screen.layout.NotesList
import com.jottie.room.screen.layout.RoomBottomButtons
import com.jottie.room.screen.layout.RoomToolbar
import com.jottie.room.screen.sheet.RoomActionsSheet
import com.jottie.room.viewmodel.NoteViewModel
import kotlinx.coroutines.flow.receiveAsFlow
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun RoomScreen(
    roomId: Long,
    modifier: Modifier = Modifier,
    onDrawerClicked: () -> Unit,
    onAudioClicked: (audioId: String) -> Unit,
    onEditorClicked: (noteId: Long?) -> Unit,
    onDeleteRoomClicked: () -> Unit,
    onRenameRoomClicked: () -> Unit
) {

    val controller = rememberRoomScreenController(roomId)
    val viewModel = koinViewModel<NoteViewModel>(key = roomId.toString()) { parametersOf(roomId) }

    val room by viewModel.roomState.collectAsState(null)
    val messages by viewModel.notes.collectAsState(emptyList())

    viewModel.event.receiveAsFlow().asEffect { event ->
        when (event) {
            is RoomEvent.OnMediaDeleted -> controller.onMediaDeleted(event.id)
            RoomEvent.OnNoteDeleted -> Unit
        }
    }

    ModalBottomSheetLayout(
        sheetState = controller.sheetState,
        sheetShape = shapes.roundedSheetShape,
        sheetContent = {
            RoomActionsSheet(
                onRenameRoomClicked = {
                    onRenameRoomClicked()
                    controller.hideSheet()
                },
                onFullScreenClicked = {
                    controller.enterFullscreen()
                    controller.hideSheet()
                },
                onViewMetricsClicked = {},
                onDeleteClicked = {
                    onDeleteRoomClicked()
                    controller.hideSheet()
                }
            )
        },
        content = {
            Box(Modifier.fillMaxSize()) {
                Column(modifier.fillMaxSize()) {

                    AnimatedVisibility(
                        visible = !controller.isFullscreen,
                        content = {
                            RoomToolbar(
                                roomTitle = room?.name
                                    ?: stringResource(Res.string.default_room_name),
                                onDrawerClicked = onDrawerClicked
                            )
                        }
                    )

                    Crossfade(
                        targetState = messages.isNotEmpty(),
                        modifier = Modifier.weight(1F),
                        content = { isNotEmpty ->
                            if (!isNotEmpty) {
                                NotesEmptyLayout()
                            } else {
                                NotesList(
                                    notes = messages,
                                    modifier = Modifier.fillMaxSize(),
                                    isFullscreen = controller.isFullscreen,
                                    onMediaClicked = { note, index ->
                                        controller.onMediaItemClicked(note.media, index)
                                    },
                                    onPlayAudioClicked = onAudioClicked,
                                    onEditNote = onEditorClicked,
                                    onDeleteNote = viewModel::deleteNote
                                )
                            }
                        }
                    )

                    AnimatedVisibility(
                        visible = !controller.isFullscreen,
                        enter = fadeIn(),
                        exit = fadeOut(),
                        content = {
                            RoomBottomButtons(
                                onNewNoteClicked = { onEditorClicked(null) },
                                onMoreClicked = controller::showSheet
                            )
                        }
                    )
                }

                if (controller.isFullscreen) {
                    ExitFullscreenPill(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .windowInsetsPadding(WindowInsets.navigationBars),
                        onCloseFullScreenClicked = controller::exitFullscreen
                    )
                }
            }
        }
    )

    AnimatedVisibility(
        visible = controller.pagerHasItems,
        modifier = Modifier.fillMaxSize(),
        enter = fadeIn(),
        exit = fadeOut(),
        content = {
            CXMediaPager(
                controller = controller.filePagerController,
                onCloseClicked = controller::clearPagerItems,
                onDownloadClicked = { pagerItem -> controller.downloadImageMedia(pagerItem.path) },
                onDeleteClicked = { viewModel.deleteFile(it.file) }
            )
        }
    )

}
