package com.jotte.room.screen.controller

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.jotte.core.LinkHandler
import com.jotte.core.LocalLinkHandler
import com.jotte.cxui.Res
import com.jotte.cxui.composition.LocalClipboardController
import com.jotte.cxui.composition.LocalToastController
import com.jotte.cxui.controller.CXToastController
import com.jotte.cxui.invalid_link_msg
import com.jotte.data.persistence.data.LinkDto
import com.jotte.room.model.data.MediaCarouselItem
import com.jotte.room.model.state.NoteState

internal class NoteController(
    private val toastController: CXToastController,
    private val linkHandler: LinkHandler,
    private val noteState: NoteState
) {

    val mediaCarouselItems =
        noteState.media.map {
            MediaCarouselItem(fileName = it.fileName, mediaId = it.mediaId)
        }

    fun hasMediaAttachments() = mediaCarouselItems.isNotEmpty()

    fun handleLinkClick(link: NoteState.LinkState) {
        when (link.type) {
            LinkDto.LinkType.Url ->
                if (!linkHandler.openUrl(link.link)) {
                    toastController.show(Res.string.invalid_link_msg)
                }

            LinkDto.LinkType.Phone ->
                if (!linkHandler.handlePhoneNumber(link.link)) {
                    toastController.show(Res.string.invalid_link_msg)
                }

            LinkDto.LinkType.Email -> Unit // TODO: Handle Email
        }
    }

}

@Composable
internal fun rememberNoteController(noteState: NoteState): NoteController {

    val toastController = LocalToastController.current
    val linkHandler = LocalLinkHandler.current

    val noteController =
        remember(noteState) {
            NoteController(
                toastController = toastController,
                linkHandler = linkHandler,
                noteState = noteState
            )
        }

    return noteController
}