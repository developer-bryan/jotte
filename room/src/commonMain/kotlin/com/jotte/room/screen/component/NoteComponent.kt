package com.jotte.room.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jotte.core.rememberFileSaverPicker
import com.jotte.cxui.Res
import com.jotte.cxui.component.CXActionPopup
import com.jotte.cxui.component.CXMediaCarousel
import com.jotte.cxui.component.CXText
import com.jotte.cxui.controller.rememberDialogController
import com.jotte.cxui.delete_note_dialog_body
import com.jotte.cxui.delete_note_dialog_title
import com.jotte.cxui.note_long_click_desc
import com.jotte.cxui.theme.colors
import com.jotte.cxui.theme.sizes
import com.jotte.cxui.theme.typography
import com.jotte.room.model.data.NotePopupActions
import com.jotte.room.model.state.NoteState
import com.jotte.room.screen.controller.NoteController
import io.github.vinceglb.filekit.extension
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun NoteComponent(
    noteState: NoteState,
    controller: NoteController,
    modifier: Modifier = Modifier,
    onImageClicked: (imageIndex: Int) -> Unit,
    onPlayAudioClicked: (audioId: String) -> Unit,
    onEditNoteClicked: () -> Unit,
    onDeleteNoteClicked: () -> Unit
) {

    val audioFileSaver =
        rememberFileSaverPicker(
            src = noteState.audio?.file,
            onSuccess = { controller.onAudioFileSaved() },
            onFailure = { _, _ -> controller.onAudioFileSaveFailure() }
        )

    val deleteNoteDialogController =
        rememberDialogController<Nothing>(
            title = Res.string.delete_note_dialog_title,
            body = Res.string.delete_note_dialog_body,
            onPositiveButtonClick = { onDeleteNoteClicked() }
        )

    Box {
        Column(
            modifier =
                modifier
                    .fillMaxWidth()
                    .background(if (controller.popupVisible) colors.accentReducedAlpha else colors.backgroundPrimary)
                    .combinedClickable(
                        onLongClickLabel = stringResource(Res.string.note_long_click_desc),
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = {},
                        onLongClick = controller::showPopup
                    ).padding(horizontal = sizes.regular)
                    .padding(vertical = sizes.small),
            verticalArrangement = Arrangement.spacedBy(sizes.small),
            horizontalAlignment = Alignment.Start,
            content = {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    content = {
                        CXText(
                            text = noteState.createdOnDate,
                            color = colors.accentColor,
                            style = typography.headerFour
                        )
                        Box(
                            modifier =
                                Modifier
                                    .padding(horizontal = sizes.extraTiny)
                                    .weight(1F)
                                    .height(1.dp)
                                    .background(colors.contentPrimaryReducedAlpha, CircleShape)
                        )
                        CXText(
                            text = noteState.createdOnTime,
                            style = typography.headerFour
                        )
                    }
                )

                noteState.content?.let { content -> NoteContentComponent(content = content) }

                noteState.audio?.let { audio ->
                    NoteAudioComponent(
                        audio = audio,
                        modifier = Modifier.align(Alignment.End),
                        onClick = { onPlayAudioClicked(audio.id) },
                        onLongClick = controller::showPopup
                    )
                }

                if (noteState.links.isNotEmpty()) {
                    NoteLinksCarousel(
                        modifier = Modifier.align(Alignment.End),
                        links = noteState.links,
                        onLinkClicked = controller::handleLinkClick
                    )
                }

                if (controller.hasMediaAttachments()) {
                    CXMediaCarousel(
                        items = controller.mediaCarouselItems,
                        modifier = Modifier.align(Alignment.End),
                        onItemClick = { onImageClicked(controller.mediaCarouselItems.indexOf(it)) }
                    )
                }
            }
        )
        if (controller.popupVisible) {
            CXActionPopup(
                alignment = Alignment.BottomStart,
                onDismissRequest = controller::hidePopup,
                actions = controller.popupActions,
                onActionClicked = {
                    when (it) {
                        NotePopupActions.CopyText -> {
                            controller.hidePopup()
                            controller.copyNoteContentToClipboard()
                        }

                        NotePopupActions.SaveAudio -> {
                            noteState.audio?.let {
                                audioFileSaver.launch(
                                    suggestedName = it.title ?: "jotte audio",
                                    extension = it.file.extension
                                )
                            }
                        }

                        NotePopupActions.Edit -> {
                            controller.hidePopup()
                            onEditNoteClicked()
                        }

                        NotePopupActions.Delete -> {
                            controller.hidePopup()
                            deleteNoteDialogController.show()
                        }
                    }
                }
            )
        }
    }
}