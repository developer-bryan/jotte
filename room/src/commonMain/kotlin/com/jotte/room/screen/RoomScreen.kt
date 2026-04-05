package com.jotte.room.screen

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
import com.jotte.cxui.Res
import com.jotte.cxui.component.CXMediaPager
import com.jotte.cxui.composition.LocalToastController
import com.jotte.cxui.default_room_name
import com.jotte.cxui.extension.asEffect
import com.jotte.cxui.media_download
import com.jotte.cxui.theme.colors
import com.jotte.cxui.theme.shapes
import com.jotte.room.model.event.RoomEvent
import com.jotte.room.model.state.RoomMetricsState
import com.jotte.room.model.state.RoomScreenSheet
import com.jotte.room.screen.component.ExitFullscreenPill
import com.jotte.room.screen.controller.rememberRoomScreenController
import com.jotte.room.screen.layout.NotesEmptyLayout
import com.jotte.room.screen.layout.NotesList
import com.jotte.room.screen.layout.RoomBottomButtons
import com.jotte.room.screen.layout.RoomToolbar
import com.jotte.room.screen.sheet.NoteActionsSheet
import com.jotte.room.screen.sheet.RoomActionsSheet
import com.jotte.room.screen.sheet.RoomMetricsSheet
import com.jotte.room.viewmodel.RoomViewModel
import io.github.vinceglb.filekit.PlatformFile
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
    val viewModel = koinViewModel<RoomViewModel>(key = roomId.toString()) { parametersOf(roomId) }

    val roomName by viewModel.roomName.collectAsState(null)
    val metrics by viewModel.roomMetricsState.collectAsState(RoomMetricsState())
    val notes by viewModel.notes.collectAsState()

    val toastController = LocalToastController.current

    viewModel.event.receiveAsFlow().asEffect { event ->
        when (event) {
            is RoomEvent.OnMediaDeleted -> controller.onMediaDeleted(event.id)
            RoomEvent.OnFileSavedToGallery -> toastController.show(Res.string.media_download)
            RoomEvent.OnFileSavedToGalleryError -> toastController.showError()
            RoomEvent.OnNoteDeleted -> Unit
        }
    }

    ModalBottomSheetLayout(
        sheetState = controller.sheetState,
        sheetShape = shapes.roundedSheetShape,
        sheetBackgroundColor = colors.backgroundPrimary,
        sheetContent = {
            when (val sheet = controller.screenSheet) {
                RoomScreenSheet.RoomActionsSheet -> {
                    RoomActionsSheet(
                        onRenameRoomClicked = {
                            onRenameRoomClicked()
                            controller.hideSheet()
                        },
                        onFullScreenClicked = {
                            controller.enterFullscreen()
                            controller.hideSheet()
                        },
                        onViewMetricsClicked = {
                            controller.showSheet(RoomScreenSheet.RoomMetricsSheet)
                        },
                        onDeleteClicked = {
                            onDeleteRoomClicked()
                            controller.hideSheet()
                        }
                    )
                }

                RoomScreenSheet.RoomMetricsSheet -> {
                    RoomMetricsSheet(metrics)
                }

                is RoomScreenSheet.NoteActionsSheet -> {
                    NoteActionsSheet(
                        params = sheet.params,
                        modifier = modifier,
                        onEditClicked = {
                            onEditorClicked(it)
                            controller.hideSheet()
                        },
                        onDeleteClicked = {
                            viewModel.deleteNote(it)
                            controller.hideSheet()
                        }
                    )
                }
            }
        },
        content = {
            Box(Modifier.fillMaxSize()) {
                Column(modifier.fillMaxSize()) {

                    AnimatedVisibility(
                        visible = !controller.isFullscreen,
                        content = {
                            RoomToolbar(
                                roomTitle =
                                    roomName
                                        ?: stringResource(Res.string.default_room_name),
                                onDrawerClicked = onDrawerClicked
                            )
                        }
                    )

                    Crossfade(
                        targetState = notes.isNotEmpty(),
                        modifier = Modifier.weight(1F),
                        content = { isNotEmpty ->
                            if (!isNotEmpty) {
                                NotesEmptyLayout()
                            } else {
                                NotesList(
                                    notes = notes,
                                    modifier = Modifier.fillMaxSize(),
                                    isFullscreen = controller.isFullscreen,
                                    onNoteLongPress = {
                                        val sheet = RoomScreenSheet.NoteActionsSheet(it)
                                        controller.showSheet(sheet)
                                    },
                                    onMediaClicked = { note, index ->
                                        controller.onMediaItemClicked(note.media, index)
                                    },
                                    onPlayAudioClicked = onAudioClicked,
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
                                onMoreClicked = {
                                    controller.showSheet(RoomScreenSheet.RoomActionsSheet)
                                }
                            )
                        }
                    )
                }

                if (controller.isFullscreen) {
                    ExitFullscreenPill(
                        modifier =
                            Modifier
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
                onDownloadClicked = { pagerItem ->
                    val file = PlatformFile(pagerItem.path)
                    viewModel.saveMediaToGallery(file)
                },
                onDeleteClicked = { viewModel.deleteFile(it.file) }
            )
        }
    )

}
