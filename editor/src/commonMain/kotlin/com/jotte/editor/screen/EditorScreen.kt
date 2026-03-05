package com.jotte.editor.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import com.jotte.camera.screen.CameraScreen
import com.jotte.core.VirtualFile
import com.jotte.core.safeWrite
import com.jotte.cxui.Res
import com.jotte.cxui.composition.LocalSoundEffectPlayer
import com.jotte.cxui.composition.LocalToastController
import com.jotte.cxui.confirm_editor_exit_dialog_body
import com.jotte.cxui.confirm_editor_exit_dialog_title
import com.jotte.cxui.controller.rememberDialogController
import com.jotte.cxui.delete_draft_audio_dialog_body
import com.jotte.cxui.delete_draft_audio_dialog_title
import com.jotte.cxui.delete_draft_file_dialog_body
import com.jotte.cxui.delete_draft_file_dialog_title
import com.jotte.cxui.generic_error_message
import com.jotte.cxui.extension.asEffect
import com.jotte.cxui.media_download
import com.jotte.cxui.soundeffect.SoundEffect
import com.jotte.cxui.soundeffect.SoundEffectsPlayer
import com.jotte.editor.model.event.EditorEvent
import com.jotte.editor.screen.component.DraftComponent
import com.jotte.editor.screen.dialog.DraftAudioTitleDialog
import com.jotte.editor.screen.dialog.CreateLinkDialog
import com.jotte.editor.screen.layout.EditorFooter
import com.jotte.editor.screen.layout.EditorHeader
import com.jotte.editor.viewmodel.EditorViewModel
import com.jotte.editor.viewmodel.NoteId
import com.jotte.editor.viewmodel.RoomId
import io.github.vinceglb.filekit.dialogs.compose.rememberFileSaverLauncher
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun EditorScreen(
    roomId: Long,
    noteId: Long?,
    onCloseClicked: () -> Unit,
    onNoteSubmitted: () -> Unit
) {

    val viewModel: EditorViewModel = koinViewModel {
        parametersOf(
            RoomId(roomId),
            NoteId(noteId)
        )
    }

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboard: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current
    val scope = rememberCoroutineScope()
    val toastState = LocalToastController.current

    val draft by viewModel.draft.collectAsState(null)
    val contentValue by viewModel.contentValue.collectAsState("")

    val soundEffectsPlayer: SoundEffectsPlayer? = LocalSoundEffectPlayer.current

    var cameraVisible by remember { mutableStateOf(false) }

    var contentEditorInFocus by remember { mutableStateOf(true) }

    val audioFileSaver = rememberFileSaverLauncher { file ->
        if (file != null) {
            scope.launch {
                runCatching { draft!!.audio!!.file.readBytes() }
                    .mapCatching { file.safeWrite(it) }
                    .onSuccess { toastState.show(Res.string.media_download) }
                    .onFailure { toastState.show(Res.string.generic_error_message) }
            }
        }
    }

    val clearDraftDialogController = rememberDialogController<Nothing>(
        title = Res.string.confirm_editor_exit_dialog_title,
        body = Res.string.confirm_editor_exit_dialog_body,
        onPositiveButtonClick = {
            onCloseClicked()
            soundEffectsPlayer?.playSound(SoundEffect.SoundEffectRemoval)
        }
    )

    val removeAttachmentDialogController = rememberDialogController<VirtualFile>(
        title = Res.string.delete_draft_file_dialog_title,
        body = Res.string.delete_draft_file_dialog_body,
        onPositiveButtonClick = { it?.let(viewModel::removeAttachment) }
    )

    val removeAudioDialogController = rememberDialogController<Unit>(
        title = Res.string.delete_draft_audio_dialog_title,
        body = Res.string.delete_draft_audio_dialog_body,
        onPositiveButtonClick = { viewModel.removeAudio() }
    )

    val audioTitleDialogController = rememberDialogController {
        DraftAudioTitleDialog(
            title = draft?.audio?.title ?: "",
            onTitleEdited = {
                viewModel.setAudioTitle(it)
                hide()
            }
        )
    }

    val linkEditorDialogController = rememberDialogController<Nothing> {
        CreateLinkDialog(
            onLinkCreated = { newLink ->
                viewModel.addLink(newLink)
                this.hide()
            }
        )
    }

    viewModel.event.consumeAsFlow().asEffect {
        when (it) {
            EditorEvent.OnDraftSubmitted -> {
                keyboard?.hide()
                onNoteSubmitted()
            }
        }
    }

    LaunchedEffect(cameraVisible) {
        if (cameraVisible) {
            focusManager.clearFocus()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.ime)
            .windowInsetsPadding(WindowInsets.systemBars),
        content = {

            EditorHeader(
                canSubmit = draft?.canSubmit ?: false,
                onSubmitClicked = viewModel::submit,
                onCloseClicked = {
                    if (draft?.canSubmit == true) {
                        clearDraftDialogController.show()
                    } else {
                        onCloseClicked()
                    }
                }
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F)
                    .verticalScroll(rememberScrollState()),
                content = {
                    DraftComponent(
                        draft = draft,
                        contentValue = contentValue,
                        focusRequester = focusRequester,
                        onContentValueChanged = viewModel::setContentValue,
                        onRemoveMedia = removeAttachmentDialogController::show,
                        onRenameAudio = audioTitleDialogController::show,
                        onSaveAudio = {
                            audioFileSaver.launch(
                                suggestedName = draft?.audio?.title ?: "jotte audio",
                                extension = draft?.audio?.file?.getExtension()
                            )
                        },
                        onRemoveAudio = removeAudioDialogController::show,
                        onRemoveLink = viewModel::removeLink,
                        onFocusChanged = { contentEditorInFocus = it.hasFocus }
                    )
                }
            )

            EditorFooter(
                contentEditorInFocus = contentEditorInFocus,
                onCameraClicked = { cameraVisible = true },
                onAudioClicked = {},
                onLinkClicked = linkEditorDialogController::show,
                toggleFocusButtonClicked = {
                    if (contentEditorInFocus) {
                        focusManager.clearFocus()
                    } else {
                        focusRequester.requestFocus()
                    }
                }
            )

        }
    )

    AnimatedVisibility(
        visible = cameraVisible,
        modifier = Modifier.fillMaxSize(),
        enter = slideInVertically { it },
        exit = slideOutVertically { it }) {
        CameraScreen(
            onCloseClicked = { cameraVisible = false },
            onMediaCaptured = {
                viewModel.addAttachment(it)
                cameraVisible = false
            }
        )
    }

}
