package com.jottie.editor.screen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.graphicsLayer
import com.jottie.core.VirtualFile
import com.jottie.cxui.Res
import com.jottie.cxui.color.Pallete
import com.jottie.cxui.component.CXButtonIcon
import com.jottie.cxui.component.CXMediaCarousel
import com.jottie.cxui.composition.LocalSoundEffectPlayer
import com.jottie.cxui.controller.rememberDialogController
import com.jottie.cxui.icon_trash
import com.jottie.cxui.remove_link_dialog_body
import com.jottie.cxui.remove_link_dialog_title
import com.jottie.cxui.soundeffect.SoundEffect
import com.jottie.cxui.soundeffect.SoundEffectsPlayer
import com.jottie.cxui.theme.sizes
import com.jottie.editor.model.state.DraftCarouselItem
import com.jottie.editor.model.state.DraftLinkState
import com.jottie.editor.model.state.DraftState

@Composable
internal fun DraftComponent(
    draft: DraftState?,
    contentValue: String,
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester,
    onContentValueChanged: (newValue: String) -> Unit,
    onRemoveMedia: (file: VirtualFile) -> Unit,
    onRenameAudio: () -> Unit,
    onSaveAudio: () -> Unit,
    onRemoveAudio: () -> Unit,
    onRemoveLink: (link: DraftLinkState) -> Unit,
    onFocusChanged: (focus: FocusState) -> Unit
) {

    val soundEffectsPlayer: SoundEffectsPlayer? = LocalSoundEffectPlayer.current

    val mediaCarouselItems = remember(draft?.media) {
        draft?.media?.map { DraftCarouselItem(it) }
    }

    val removeLinkDialogController = rememberDialogController<DraftLinkState>(
        title = Res.string.remove_link_dialog_title,
        body = Res.string.remove_link_dialog_body,
        onPositiveButtonClick = {
            it?.let(onRemoveLink)
            soundEffectsPlayer?.playSound(SoundEffect.SoundEffectRemoval)
        }
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = sizes.extraTiny)
            .padding(start = sizes.small)
            .padding(end = sizes.regular)
            .padding(vertical = sizes.small),
        verticalArrangement = Arrangement.spacedBy(sizes.small),
        horizontalAlignment = Alignment.Start,
        content = {

            DraftContentComponent(
                value = contentValue,
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged(onFocusChanged),
                focusRequester = focusRequester,
                onValueChanged = onContentValueChanged,
            )

            draft?.audio?.let {
                DraftAudioComponent(
                    audio = it,
                    onRenameClicked = onRenameAudio,
                    onSaveClicked = onSaveAudio,
                    onRemoveClicked = onRemoveAudio
                )
            }

            draft?.links?.let {
                DraftLinksCarousel(
                    links = it,
                    onRemoveLinkClicked = { removeLinkDialogController.show(it) }
                )
            }

            if (mediaCarouselItems?.isNotEmpty() == true) {
                CXMediaCarousel(
                    items = mediaCarouselItems,
                    itemDrawOver = { carouselItem ->
                        CXButtonIcon(
                            icon = Res.drawable.icon_trash,
                            backgroundColor = Pallete.LightBlack,
                            iconColor = Pallete.MilkWhite,
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .graphicsLayer(
                                    rotationZ = 180f,
                                    scaleX = -0.8F,
                                    scaleY = -0.8F
                                ),
                            onClick = { onRemoveMedia(carouselItem.file) }
                        )
                    }
                )
            }

        }
    )
}