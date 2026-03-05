package com.jottie.room.screen.controller

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.jottie.core.LinkHandler
import com.jottie.core.LocalLinkHandler
import com.jottie.core.safeWrite
import com.jottie.cxui.Res
import com.jottie.cxui.composition.LocalClipboardController
import com.jottie.cxui.composition.LocalSoundEffectPlayer
import com.jottie.cxui.composition.LocalToastController
import com.jottie.cxui.controller.CXClipboardController
import com.jottie.cxui.controller.CXToastController
import com.jottie.cxui.generic_error_message
import com.jottie.cxui.invalid_link_msg
import com.jottie.cxui.media_download
import com.jottie.cxui.soundeffect.SoundEffect
import com.jottie.message.data.LinkDto
import com.jottie.room.model.data.MediaCarouselItem
import com.jottie.room.model.data.NotePopupActions
import com.jottie.room.model.state.NoteState
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