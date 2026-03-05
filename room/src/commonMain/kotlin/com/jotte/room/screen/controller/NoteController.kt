package com.jotte.room.screen.controller

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.jotte.core.LinkHandler
import com.jotte.core.LocalLinkHandler
import com.jotte.core.safeWrite
import com.jotte.cxui.Res
import com.jotte.cxui.composition.LocalClipboardController
import com.jotte.cxui.composition.LocalSoundEffectPlayer
import com.jotte.cxui.composition.LocalToastController
import com.jotte.cxui.controller.CXClipboardController
import com.jotte.cxui.controller.CXToastController
import com.jotte.cxui.generic_error_message
import com.jotte.cxui.invalid_link_msg
import com.jotte.cxui.media_download
import com.jotte.cxui.soundeffect.SoundEffect
import com.jotte.message.data.LinkDto
import com.jotte.room.model.data.MediaCarouselItem
import com.jotte.room.model.data.NotePopupActions
import com.jotte.room.model.state.NoteState
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.readBytes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

internal class NoteController(
    private val scope: CoroutineScope,
    private val toastController: CXToastController,
    private val clipboardController: CXClipboardController,
    private val linkHandler: LinkHandler,
    private val noteState: NoteState
) {

    val mediaCarouselItems = noteState.media.map {
        MediaCarouselItem(fileName = it.fileName, mediaId = it.mediaId)
    }

    val popupActions = buildList {
        if (noteState.content != null) {
            add(NotePopupActions.CopyText)
        }
        if (noteState.audio != null) {
            add(NotePopupActions.SaveAudio)
        }
        add(NotePopupActions.Edit)
        add(NotePopupActions.Delete)
    }

    var popupVisible by mutableStateOf(false)

    fun saveAudio(file: PlatformFile) {
        scope.launch {
            runCatching { noteState.audio!!.file.readBytes() }
                .mapCatching { file.safeWrite(it) }
                .onSuccess {
                    popupVisible = false
                    toastController.show(Res.string.media_download)
                }
                .onFailure {
                    popupVisible = false
                    toastController.show(Res.string.generic_error_message)
                }
        }
    }

    fun showPopup() { popupVisible = true }

    fun hidePopup() { popupVisible = false }

    fun hasMediaAttachments() = mediaCarouselItems.isNotEmpty()

    fun handleLinkClick(link: NoteState.LinkState) {
        when (link.type) {
            LinkDto.LinkType.Url -> if (!linkHandler.openUrl(link.link)) {
                toastController.show(Res.string.invalid_link_msg)
            }

            LinkDto.LinkType.Phone -> if (!linkHandler.handlePhoneNumber(link.link)) {
                toastController.show(Res.string.invalid_link_msg)
            }

            LinkDto.LinkType.Email -> Unit // TODO: Handle Email
        }
    }

    fun copyNoteContentToClipboard() {
        noteState.content?.value?.let {
            clipboardController.copyToClipboard(it)
        }
    }

}

@Composable
internal fun rememberNoteController(noteState: NoteState): NoteController {

    val soundEffectsPlayer = LocalSoundEffectPlayer.current
    val scope = rememberCoroutineScope()
    val toastController = LocalToastController.current
    val clipboard = LocalClipboardController.current
    val linkHandler = LocalLinkHandler.current

    val noteController = remember(noteState) {
        NoteController(
            scope = scope,
            toastController = toastController,
            clipboardController = clipboard,
            linkHandler = linkHandler,
            noteState = noteState
        )
    }

    LaunchedEffect(noteController.popupVisible) {
        if (noteController.popupVisible) {
            soundEffectsPlayer?.playSound(SoundEffect.SoundEffectLongPress)
        }
    }

    return noteController
}