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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toIntSize
import com.jotte.cxui.Res
import com.jotte.cxui.component.CXMediaCarousel
import com.jotte.cxui.component.CXText
import com.jotte.cxui.composition.LocalSoundEffectPlayer
import com.jotte.cxui.modifier.captureBitmap
import com.jotte.cxui.note_long_click_desc
import com.jotte.cxui.soundeffect.SoundEffect
import com.jotte.cxui.theme.colors
import com.jotte.cxui.theme.sizes
import com.jotte.cxui.theme.typography
import com.jotte.room.model.data.NoteActionsSheetParams
import com.jotte.room.model.state.NoteState
import com.jotte.room.screen.controller.NoteController
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import kotlin.math.min

@Composable
internal fun NoteComponent(
    noteState: NoteState,
    controller: NoteController,
    modifier: Modifier = Modifier,
    onLongPress: (params: NoteActionsSheetParams) -> Unit,
    onImageClicked: (imageIndex: Int) -> Unit,
    onPlayAudioClicked: (audioId: String) -> Unit,
) {

    val scope = rememberCoroutineScope()
    val graphicsLayer = rememberGraphicsLayer()
    val soundEffectsPlayer = LocalSoundEffectPlayer.current
    val sizes = sizes

    Box(
        modifier =
            modifier
                .captureBitmap(
                    graphicsLayer = graphicsLayer,
                    sizeProvider = {
                        val width = it.size.width
                        val height = min(it.size.height, width * sizes.aspectRatio916)

                        Size(width, height).toIntSize()
                    }
                ),
        content = {
            Column(
                modifier =
                    modifier
                        .fillMaxWidth()
                        .combinedClickable(
                            onLongClickLabel = stringResource(Res.string.note_long_click_desc),
                            interactionSource = remember { MutableInteractionSource() },
                            onClick = {},
                            onLongClick = {
                                scope
                                    .launch {
                                        val bmp = graphicsLayer.toImageBitmap()
                                        val params =
                                            NoteActionsSheetParams(
                                                noteState = noteState,
                                                bannerImage = bmp,
                                                target = null
                                            )

                                        onLongPress(params)
                                    }.invokeOnCompletion {
                                        soundEffectsPlayer?.playSound(
                                            SoundEffect.SoundEffectLongPress
                                        )
                                    }
                            }
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
                            onLongClick = {}
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
        }
    )
}