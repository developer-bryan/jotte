package com.jotte.editor.screen.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.jotte.cxui.Res
import com.jotte.cxui.bold_toggle_click_label
import com.jotte.cxui.component.CXButtonIcon
import com.jotte.cxui.extension.RowExtension.FillSpace
import com.jotte.cxui.header_toggle_click_label
import com.jotte.cxui.icon_camera
import com.jotte.cxui.icon_chevron_down
import com.jotte.cxui.icon_chevron_up
import com.jotte.cxui.icon_link
import com.jotte.cxui.icon_mic
import com.jotte.cxui.theme.colors
import com.jotte.cxui.theme.shapes
import com.jotte.cxui.theme.sizes
import com.jotte.editor.model.state.ContentSpan
import com.jotte.editor.screen.component.ButtonOptionContentFormat
import com.mohamedrejeb.richeditor.model.RichTextState
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun EditorFooter(
    state: RichTextState,
    modifier: Modifier = Modifier,
    contentEditorInFocus: Boolean = false,
    onLinkClicked: () -> Unit,
    onCameraClicked: () -> Unit,
    onAudioClicked: () -> Unit,
    toggleFocusButtonClicked: () -> Unit
) {

    var boldToggled by rememberSaveable { mutableStateOf(false) }
    var headerToggled by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(state.currentSpanStyle) {
        val currentFontWeight = state.currentSpanStyle.fontWeight

        boldToggled = (currentFontWeight == ContentSpan.Bold.spanStyle.fontWeight)
        headerToggled = (currentFontWeight == ContentSpan.Header.spanStyle.fontWeight)
    }

    Row(
        modifier =
            modifier
                .height(sizes.interactableHeight)
                .background(
                    color = colors.backgroundPrimary,
                    shape = shapes.roundedContentFormatterTrayShape
                ).padding(horizontal = sizes.extraSmall),
        verticalAlignment = Alignment.CenterVertically,
        content = {

            ButtonOptionContentFormat(
                option = 'H',
                onClickLabel = stringResource(Res.string.header_toggle_click_label),
                isToggled = headerToggled,
                onClick = { state.toggleSpanStyle(ContentSpan.Header.spanStyle) }
            )

            ButtonOptionContentFormat(
                option = 'B',
                onClickLabel = stringResource(Res.string.bold_toggle_click_label),
                isToggled = boldToggled,
                onClick = { state.toggleSpanStyle(ContentSpan.Bold.spanStyle) }
            )

            CXButtonIcon(
                icon = Res.drawable.icon_link,
                onClick = onLinkClicked
            )

            CXButtonIcon(
                icon = Res.drawable.icon_camera,
                onClick = onCameraClicked
            )

            CXButtonIcon(
                icon = Res.drawable.icon_mic,
                onClick = onAudioClicked
            )

            FillSpace()

            CXButtonIcon(
                icon = if (contentEditorInFocus) Res.drawable.icon_chevron_down else Res.drawable.icon_chevron_up,
                onClick = toggleFocusButtonClicked
            )

        }
    )
}