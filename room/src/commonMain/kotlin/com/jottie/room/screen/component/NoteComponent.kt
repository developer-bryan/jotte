package com.jottie.room.screen.component

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
import com.jottie.cxui.Res
import com.jottie.cxui.component.CXActionPopup
import com.jottie.cxui.component.CXMediaCarousel
import com.jottie.cxui.component.CXText
import com.jottie.cxui.controller.rememberDialogController
import com.jottie.cxui.delete_note_dialog_body
import com.jottie.cxui.delete_note_dialog_title
import com.jottie.cxui.note_long_click_desc
import com.jottie.cxui.theme.colors
import com.jottie.cxui.theme.sizes
import com.jottie.cxui.theme.typography
import com.jottie.room.model.data.NotePopupActions
import com.jottie.room.model.state.NoteState
import com.jottie.room.screen.controller.NoteController
import io.github.vinceglb.filekit.dialogs.compose.rememberFileSaverLauncher
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

    val audioFileSaver = rememberFileSaverLauncher { file ->
        if (file != null) {
            controller.saveAudio(file)
        }
    }

    val deleteNoteDialogController = rememberDialogController<Nothing>(
        title = Res.string.delete_note_dialog_title,
        body = Res.string.delete_note_dialog_body,
        onPositiveButtonClick = { onDeleteNoteClicked() }
    )

    Box {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(if (controller.popupVisible) colors.accentReducedAlpha else colors.backgroundPrimary)
                .combinedClickable(
                    onLongClickLabel = stringResource(Res.string.note_long_click_desc),
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = {},
                    onLongClick = controller::showPopup
                )
                .padding(horizontal = sizes.regular)
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
                            modifier = Modifier
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
                        audioState = audio,
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
                                    suggestedName = it.title ?: "jottie audio",
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