package com.jottie.editor.screen.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.jottie.cxui.Res
import com.jottie.cxui.component.CXButtonIcon
import com.jottie.cxui.extension.RowExtension.FillSpace
import com.jottie.cxui.icon_chevron_down
import com.jottie.cxui.icon_chevron_up
import com.jottie.cxui.theme.colors
import com.jottie.cxui.theme.sizes
import com.jottie.editor.screen.component.ButtonAudio
import com.jottie.editor.screen.component.ButtonCamera
import com.jottie.editor.screen.component.ButtonLink

@Composable
internal fun EditorFooter(
    modifier: Modifier = Modifier,
    contentEditorInFocus: Boolean = true,
    onCameraClicked: () -> Unit,
    onAudioClicked: () -> Unit,
    onLinkClicked: () -> Unit,
    toggleFocusButtonClicked: () -> Unit
) {

    Row(
        modifier = modifier
            .padding(horizontal = sizes.regular)
            .padding(vertical = sizes.small),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(sizes.tiny),
        content = {
            ButtonCamera(onClick = onCameraClicked)
            ButtonAudio(onClick = onAudioClicked)
            ButtonLink(onClick = onLinkClicked)
            FillSpace()
            CXButtonIcon(
                icon = if (contentEditorInFocus) Res.drawable.icon_chevron_down else Res.drawable.icon_chevron_up,
                backgroundColor = colors.backgroundSecondary,
                onClick = toggleFocusButtonClicked
            )
        }
    )

}