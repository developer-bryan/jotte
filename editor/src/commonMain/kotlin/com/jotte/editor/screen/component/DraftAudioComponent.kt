package com.jotte.editor.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.jotte.cxui.Res
import com.jotte.cxui.component.CXButtonIcon
import com.jotte.cxui.component.CXIcon
import com.jotte.cxui.component.CXText
import com.jotte.cxui.icon_audio_wave
import com.jotte.cxui.icon_remove
import com.jotte.cxui.icon_save
import com.jotte.cxui.icon_text
import com.jotte.cxui.theme.colors
import com.jotte.cxui.theme.sizes
import com.jotte.cxui.theme.typography
import com.jotte.editor.model.state.DraftAudioState

@Composable
internal fun DraftAudioComponent(
    audio: DraftAudioState,
    modifier: Modifier = Modifier,
    onRenameClicked: () -> Unit,
    onSaveClicked: () -> Unit,
    onRemoveClicked: () -> Unit
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        content = {

            CXButtonIcon(
                icon = Res.drawable.icon_remove,
                iconColor = colors.negativeColor,
                onClick = onRemoveClicked
            )

            CXButtonIcon(
                icon = Res.drawable.icon_save,
                onClick = onSaveClicked
            )

            CXButtonIcon(
                icon = Res.drawable.icon_text,
                onClick = onRenameClicked
            )

            Row(
                modifier = Modifier
                    .height(sizes.interactableHeight)
                    .background(colors.backgroundSecondary, CircleShape)
                    .padding(sizes.small),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(sizes.extraSmall),
                content = {
                    CXIcon(Res.drawable.icon_audio_wave)
                    CXText(
                        text = audio.title ?: audio.file.fileName,
                        style = typography.bodyOne
                    )
                }
            )
        }
    )

}