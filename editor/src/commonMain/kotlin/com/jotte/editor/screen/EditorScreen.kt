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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import com.jotte.camera.screen.CameraScreen
import com.jotte.core.rememberFileSaverPicker
import com.jotte.cxui.Res
import com.jotte.cxui.cancel_recording_dialog_body
import com.jotte.cxui.cancel_recording_dialog_title
import com.jotte.cxui.color.CXDarkColors
import com.jotte.cxui.composition.LocalColor
import com.jotte.cxui.composition.LocalSoundEffectPlayer
import com.jotte.cxui.composition.LocalToastController
import com.jotte.cxui.confirm_editor_exit_dialog_body
import com.jotte.cxui.confirm_editor_exit_dialog_title
import com.jotte.cxui.controller.rememberDialogController
import com.jotte.cxui.delete_draft_audio_dialog_body
import com.jotte.cxui.delete_draft_audio_dialog_title
import com.jotte.cxui.delete_draft_file_dialog_body
import com.jotte.cxui.delete_draft_file_dialog_title
import com.jotte.cxui.extension.asEffect
import com.jotte.cxui.generic_error_message
import com.jotte.cxui.media_download
import com.jotte.cxui.soundeffect.SoundEffect
import com.jotte.cxui.soundeffect.SoundEffectsPlayer
import com.jotte.cxui.theme.sizes
import com.jotte.editor.controller.rememberRecordAudioController
import com.jotte.editor.model.event.EditorEvent
import com.jotte.editor.screen.component.DraftComponent
import com.jotte.editor.screen.dialog.CreateAppLinkDialog
import com.jotte.editor.screen.dialog.DraftAudioTitleDialog
import com.jotte.editor.screen.layout.AudioRecordingChip
import com.jotte.editor.screen.layout.EditorFooter
import com.jotte.editor.screen.layout.EditorHeader
import com.jotte.editor.viewmodel.EditorViewModel
import com.jotte.editor.viewmodel.NoteId
import com.jotte.editor.viewmodel.RoomId
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.extension
import io.github.vinceglb.filekit.name
import kotlinx.coroutines.flow.consumeAsFlow
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun EditorScreen(
    roomId: Long,
    noteId: Long?,
    onCloseClicked: () -> Unit,
    onNoteSubmitted: () -> Unit
) {

    val viewModel: EditorViewModel =
        koinViewModel {
            parametersOf(
                RoomId(roomId),
                NoteId(noteId)
            )
        }

    val audioController = rememberRecordAudioController(viewModel::addAudioFile)

    val richTextState = rememberRichTextState()

    val focusManager = LocalFocusManager.current
    val toastController = LocalToastController.current
    val soundEffectsPlayer: SoundEffectsPlayer? = LocalSoundEffectPlayer.current
    val keyboard: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current

    val focusRequester = remember { FocusRequester() }

    val snapshot by viewModel.snapshot.collectAsState(null)
    val draft by viewModel.draft.collectAsState(null)
    val isAudioRecording by audioController.isRecording.collectAsState(false)

    var cameraVisible by remember { mutableStateOf(false) }
    var contentEditorInFocus by remember { mutableStateOf(true) }

    val audioFileSaver =
        rememberFileSaverPicker(
            src = draft?.audio?.file,
            onSuccess = { toastController.show(Res.string.media_download) },
            onFailure = { _, _ -> toastController.show(Res.string.generic_error_message) }
        )

    val clearDraftDialogController =
        rememberDialogController<Nothing>(
            title = Res.string.confirm_editor_exit_dialog_title,
            body = Res.string.confirm_editor_exit_dialog_body,
            onPositiveButtonClick = {
                onCloseClicked()
                soundEffectsPlayer?.playSound(SoundEffect.SoundEffectRemoval)
            }
        )

    val removeAttachmentDialogController =
        rememberDialogController<PlatformFile>(
            title = Res.string.delete_draft_file_dialog_title,
            body = Res.string.delete_draft_file_dialog_body,
            onPositiveButtonClick = { it?.let(viewModel::removeAttachment) }
        )

    val removeAudioDialogController =
        rememberDialogController<Unit>(
            title = Res.string.delete_draft_audio_dialog_title,
            body = Res.string.delete_draft_audio_dialog_body,
            onPositiveButtonClick = { viewModel.removeAudio() }
        )

    val audioTitleDialogController =
        rememberDialogController {
            DraftAudioTitleDialog(
                title = draft?.audio?.title ?: "",
                onTitleEdited = {
                    viewModel.setAudioTitle(it)
                    hide()
                }
            )
        }

    val linkEditorDialogController =
        rememberDialogController {
            CreateAppLinkDialog(
                onLinkCreated = { appLink ->
                    richTextState.addLink(
                        text = appLink.name ?: appLink.link,
                        url = appLink.uri
                    )

                    this.hide()
                }
            )
        }

    val cancelAudioRecordingAndCloseDialogController =
        rememberDialogController<Nothing>(
            title = Res.string.cancel_recording_dialog_title,
            body = Res.string.cancel_recording_dialog_body,
            onPositiveButtonClick = { onCloseClicked() }
        )

    viewModel.event.consumeAsFlow().asEffect {
        when (it) {
            EditorEvent.OnDraftSubmitted -> {
                keyboard?.hide()
                onNoteSubmitted()
            }
        }
    }

    LaunchedEffect(snapshot) {
        snapshot?.note?.content?.value?.let { html ->
            richTextState.setHtml(html)
        }
    }

    LaunchedEffect(cameraVisible) {
        if (cameraVisible) {
            focusManager.clearFocus()
        }
    }

    LaunchedEffect(richTextState.annotatedString) {
        viewModel.setContentValue(richTextState.toHtml())
    }

    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.ime)
                .windowInsetsPadding(WindowInsets.systemBars),
        content = {

            EditorHeader(
                canSubmit = draft?.canSubmit ?: false,
                onSubmitClicked = viewModel::submit,
                onCloseClicked = {
                    if (isAudioRecording) {
                        cancelAudioRecordingAndCloseDialogController.show()
                    } else if (draft?.canSubmit == true) {
                        clearDraftDialogController.show()
                    } else {
                        onCloseClicked()
                    }
                }
            )

            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .weight(1F)
                        .verticalScroll(rememberScrollState()),
                content = {
                    DraftComponent(
                        draft = draft,
                        richTextState = richTextState,
                        focusRequester = focusRequester,
                        onRemoveMedia = removeAttachmentDialogController::show,
                        onRenameAudio = audioTitleDialogController::show,
                        onSaveAudio = {
                            draft?.audio?.let {
                                audioFileSaver.launch(
                                    suggestedName = it.title ?: it.file.name,
                                    extension = it.file.extension
                                )
                            }
                        },
                        onRemoveAudio = removeAudioDialogController::show,
                        onRemoveLink = viewModel::removeLink,
                        onFocusChanged = { contentEditorInFocus = it.hasFocus }
                    )
                }
            )

            if (audioController.isAudioRecorderOpen) {
                AudioRecordingChip(
                    controller = audioController,
                    modifier =
                        Modifier
                            .padding(bottom = sizes.small)
                            .padding(horizontal = sizes.regular)
                )
            } else {
                CompositionLocalProvider(
                    value = LocalColor provides CXDarkColors(),
                    content = {
                        EditorFooter(
                            state = richTextState,
                            modifier =
                                Modifier
                                    .padding(sizes.small)
                                    .fillMaxWidth()
                                    .align(Alignment.CenterHorizontally),
                            contentEditorInFocus = contentEditorInFocus,
                            onLinkClicked = linkEditorDialogController::show,
                            onCameraClicked = { cameraVisible = true },
                            onAudioClicked = audioController::checkAudioPermission,
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
            }
        }
    )

    AnimatedVisibility(
        visible = cameraVisible,
        modifier = Modifier.fillMaxSize(),
        enter = slideInVertically { it },
        exit = slideOutVertically { it },
        content = {
            CameraScreen(
                onCloseClicked = { cameraVisible = false },
                onMediaCaptured = {
                    viewModel.addAttachment(it)
                    cameraVisible = false
                }
            )
        }
    )

}
