package com.jotte.editor.screen.component

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
import com.jotte.cxui.Res
import com.jotte.cxui.color.Pallete
import com.jotte.cxui.component.CXButtonIcon
import com.jotte.cxui.component.CXMediaCarousel
import com.jotte.cxui.composition.LocalSoundEffectPlayer
import com.jotte.cxui.controller.rememberDialogController
import com.jotte.cxui.icon_trash
import com.jotte.cxui.remove_link_dialog_body
import com.jotte.cxui.remove_link_dialog_title
import com.jotte.cxui.soundeffect.SoundEffect
import com.jotte.cxui.soundeffect.SoundEffectsPlayer
import com.jotte.cxui.theme.sizes
import com.jotte.editor.model.state.DraftCarouselItem
import com.jotte.editor.model.state.DraftLinkState
import com.jotte.editor.model.state.DraftState
import com.mohamedrejeb.richeditor.model.RichTextState
import io.github.vinceglb.filekit.PlatformFile

@Composable
internal fun DraftComponent(
    draft: DraftState?,
    richTextState: RichTextState,
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester,
    onRemoveMedia: (file: PlatformFile) -> Unit,
    onRenameAudio: () -> Unit,
    onSaveAudio: () -> Unit,
    onRemoveAudio: () -> Unit,
    onRemoveLink: (link: DraftLinkState) -> Unit,
    onFocusChanged: (focus: FocusState) -> Unit
) {

    val soundEffectsPlayer: SoundEffectsPlayer? = LocalSoundEffectPlayer.current

    val mediaCarouselItems =
        remember(draft?.media) {
            draft?.media?.map { DraftCarouselItem(it) }
        }

    val removeLinkDialogController =
        rememberDialogController<DraftLinkState>(
            title = Res.string.remove_link_dialog_title,
            body = Res.string.remove_link_dialog_body,
            onPositiveButtonClick = {
                it?.let(onRemoveLink)
                soundEffectsPlayer?.playSound(SoundEffect.SoundEffectRemoval)
            }
        )

    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(start = sizes.extraTiny)
                .padding(start = sizes.small)
                .padding(end = sizes.regular)
                .padding(vertical = sizes.small),
        verticalArrangement = Arrangement.spacedBy(sizes.small),
        horizontalAlignment = Alignment.Start,
        content = {

            DraftContentComponent(
                state = richTextState,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .onFocusChanged(onFocusChanged),
                focusRequester = focusRequester,
            )

            draft?.audio?.let {
                DraftAudioComponent(
                    audio = it,
                    modifier = modifier.align(Alignment.End),
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
                    modifier = Modifier.align(Alignment.End),
                    itemDrawOver = { carouselItem ->
                        CXButtonIcon(
                            icon = Res.drawable.icon_trash,
                            backgroundColor = Pallete.LightBlack,
                            iconColor = Pallete.MilkWhite,
                            modifier =
                                Modifier
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