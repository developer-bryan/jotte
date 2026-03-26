package com.jotte.editor.screen.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.jotte.cxui.Res
import com.jotte.cxui.component.CXButtonIcon
import com.jotte.cxui.extension.RowExtension.FillSpace
import com.jotte.cxui.icon_chevron_down
import com.jotte.cxui.icon_chevron_up
import com.jotte.cxui.theme.colors
import com.jotte.cxui.theme.sizes
import com.jotte.editor.screen.component.ButtonAudio
import com.jotte.editor.screen.component.ButtonCamera
import com.jotte.editor.screen.component.ButtonLink

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
        modifier =
            modifier
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