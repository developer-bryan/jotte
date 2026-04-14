package com.jotte.room.screen.sheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.jotte.core.rememberFileSaverPicker
import com.jotte.cxui.Res
import com.jotte.cxui.component.CXButtonOption
import com.jotte.cxui.composition.LocalClipboardController
import com.jotte.cxui.composition.LocalToastController
import com.jotte.cxui.controller.rememberDialogController
import com.jotte.cxui.delete_note_dialog_body
import com.jotte.cxui.delete_note_dialog_title
import com.jotte.cxui.icon_copy
import com.jotte.cxui.icon_edit
import com.jotte.cxui.icon_save
import com.jotte.cxui.icon_trash
import com.jotte.cxui.media_download
import com.jotte.cxui.note_action_copy_text
import com.jotte.cxui.note_action_delete_note
import com.jotte.cxui.note_action_edit_note
import com.jotte.cxui.note_action_save_audio
import com.jotte.cxui.theme.colors
import com.jotte.cxui.theme.shapes
import com.jotte.cxui.theme.sizes
import com.jotte.room.model.data.NoteActionsSheetParams
import com.jotte.room.model.state.NoteState
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import io.github.vinceglb.filekit.extension
import io.github.vinceglb.filekit.name
import org.jetbrains.compose.resources.stringResource

@Composable
@Suppress("MagicNumber")
internal fun NoteActionsSheet(
    params: NoteActionsSheetParams,
    modifier: Modifier = Modifier,
    onEditClicked: (id: Long) -> Unit,
    onDeleteClicked: (id: Long) -> Unit
) {

    val clipboardController = LocalClipboardController.current
    val toastController = LocalToastController.current

    val richTextState = rememberRichTextState()

    val deleteNoteDialogController =
        rememberDialogController<NoteState>(
            title = Res.string.delete_note_dialog_title,
            body = Res.string.delete_note_dialog_body,
            onPositiveButtonClick = { it?.let { onDeleteClicked(it.noteId) } }
        )

    val audioFileSaver =
        rememberFileSaverPicker(
            src = params.noteState.audio?.file,
            onSuccess = { toastController.show(Res.string.media_download) },
            onFailure = { _, _ -> toastController.showError() }
        )

    fun copyNoteContentToClipboard() {
        params.noteState.content?.value?.let {
            richTextState.setHtml(it)
            clipboardController.copyToClipboard(richTextState.annotatedString.text)
        }
    }

    Column(
        modifier =
            modifier
                .windowInsetsPadding(WindowInsets.navigationBars)
                .padding(top = sizes.extraSmall),
        content = {

            params.bannerImage?.let {
                Image(
                    bitmap = it,
                    contentDescription = null,
                    modifier =
                        Modifier
                            .align(Alignment.CenterHorizontally)
                            .fillMaxWidth(0.90F)
                            .clip(shapes.mediaPreviewShape)
                )
            }

            params.noteState.audio?.let {
                CXButtonOption(
                    label = stringResource(Res.string.note_action_save_audio),
                    icon = Res.drawable.icon_save,
                    onClick = {
                        audioFileSaver.launch(
                            suggestedName = it.title ?: it.file.name,
                            extension = it.file.extension
                        )
                    }
                )
            }

            params.noteState.content?.let {
                CXButtonOption(
                    label = stringResource(Res.string.note_action_copy_text),
                    icon = Res.drawable.icon_copy,
                    onClick = { copyNoteContentToClipboard() }
                )
            }

            CXButtonOption(
                label = stringResource(Res.string.note_action_edit_note),
                icon = Res.drawable.icon_edit,
                onClick = { onEditClicked(params.noteState.noteId) }
            )

            CXButtonOption(
                label = stringResource(Res.string.note_action_delete_note),
                icon = Res.drawable.icon_trash,
                contentTint = colors.negativeColor,
                onClick = { deleteNoteDialogController.show(params.noteState) }
            )

        }
    )

}