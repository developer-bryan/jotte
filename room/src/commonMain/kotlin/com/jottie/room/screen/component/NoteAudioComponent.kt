package com.jottie.room.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jottie.core.datetime.toFormattedRuntime
import com.jottie.cxui.Res
import com.jottie.cxui.component.CXIcon
import com.jottie.cxui.component.CXText
import com.jottie.cxui.extension.RowExtension.FillSpace
import com.jottie.cxui.icon_audio_note
import com.jottie.cxui.theme.CXTheme
import com.jottie.cxui.theme.colors
import com.jottie.cxui.theme.shapes
import com.jottie.cxui.theme.sizes
import com.jottie.cxui.theme.typography
import com.jottie.room.model.state.NoteState
import io.github.vinceglb.filekit.PlatformFile

@Composable
internal fun NoteAudioComponent(
    audioState: NoteState.AudioState,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onLongClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(sizes.interactableHeight)
            .background(
                color = colors.backgroundSecondary,
                shape = shapes.mediaPreviewShape
            )
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            )
            .padding(horizontal = sizes.small),
        verticalAlignment = Alignment.CenterVertically,
        content = {
            CXIcon(Res.drawable.icon_audio_note)
            Spacer(Modifier.width(sizes.extraSmall))
            CXText(
                text = audioState.title ?: "audio note",
                typography.bodyOne
            )
            FillSpace()
            CXText(
                text = audioState.duration.toFormattedRuntime(),
                style = typography.bodyTwo
            )
        }
    )
}

@Composable
@Preview
private fun Preview() {
    CXTheme(isDarkMode = true) {
        NoteAudioComponent(
            audioState = NoteState.AudioState(
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