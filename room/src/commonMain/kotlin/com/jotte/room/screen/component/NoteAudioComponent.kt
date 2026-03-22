package com.jotte.room.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jotte.core.datetime.toFormattedRuntime
import com.jotte.cxui.Res
import com.jotte.cxui.component.CXIcon
import com.jotte.cxui.component.CXText
import com.jotte.cxui.extension.RowExtension.FillSpace
import com.jotte.cxui.icon_audio_note
import com.jotte.cxui.icon_audio_wave
import com.jotte.cxui.theme.CXTheme
import com.jotte.cxui.theme.colors
import com.jotte.cxui.theme.shapes
import com.jotte.cxui.theme.sizes
import com.jotte.cxui.theme.typography
import com.jotte.room.model.state.NoteState
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.name

@Composable
internal fun NoteAudioComponent(
    audio: NoteState.AudioState,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onLongClick: () -> Unit
) {

    Row(
        modifier = modifier
            .height(sizes.interactableHeight)
            .background(colors.backgroundSecondary, CircleShape)
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            )
            .padding(sizes.small),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(sizes.extraSmall),
        content = {
            CXIcon(Res.drawable.icon_audio_wave)
            CXText(
                text = audio.title ?: audio.file.name,
                style = typography.bodyOne
            )
        }
    )
}

@Composable
@Preview
private fun Preview() {
    CXTheme(isDarkMode = true) {
        NoteAudioComponent(
            audio = NoteState.AudioState(
                id = "",
                file = PlatformFile(""),
                duration = 1000,
                title = "",
            ),
            onClick = {},
            onLongClick = {}
        )
    }
}