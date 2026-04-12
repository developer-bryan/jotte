package com.jotte.editor.screen.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import com.jotte.cxui.Res
import com.jotte.cxui.bold_toggle_click_label
import com.jotte.cxui.component.CXButtonIcon
import com.jotte.cxui.header_toggle_click_label
import com.jotte.cxui.icon_camera
import com.jotte.cxui.icon_chevron_down
import com.jotte.cxui.icon_chevron_up
import com.jotte.cxui.icon_link
import com.jotte.cxui.icon_mic
import com.jotte.cxui.theme.colors
import com.jotte.cxui.theme.shapes
import com.jotte.cxui.theme.sizes
import com.jotte.cxui.theme.typography
import com.jotte.editor.screen.component.ButtonOptionContentFormat
import com.mohamedrejeb.richeditor.model.RichTextState
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun EditorOptionsTray(
    state: RichTextState,
    modifier: Modifier = Modifier,
    contentEditorInFocus: Boolean = false,
    onLinkClicked: () -> Unit,
    onCameraClicked: () -> Unit,
    onAudioClicked: () -> Unit,
    toggleFocusButtonClicked: () -> Unit
) {

    val colors = colors
    val typography = typography

    var boldToggled by rememberSaveable { mutableStateOf(false) }
    var headerToggled by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(boldToggled) {
        val fontWeight = if (boldToggled) FontWeight.Bold else FontWeight.Normal
        state.toggleSpanStyle(SpanStyle(fontWeight = fontWeight))
    }

    LaunchedEffect(headerToggled) {
        val style = if (headerToggled) typography.headerTwo else typography.bodyOne
        state.toggleSpanStyle(style.toSpanStyle())
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
        horizontalArrangement = Arrangement.spacedBy(sizes.small),
        content = {

            ButtonOptionContentFormat(
                option = 'H',
                onClickLabel = stringResource(Res.string.header_toggle_click_label),
                isToggled = headerToggled,
                onClick = { headerToggled = !headerToggled }
            )

            ButtonOptionContentFormat(
                option = 'B',
                onClickLabel = stringResource(Res.string.bold_toggle_click_label),
                isToggled = boldToggled,
                onClick = { boldToggled = !boldToggled }
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

            CXButtonIcon(
                icon = if (contentEditorInFocus) Res.drawable.icon_chevron_down else Res.drawable.icon_chevron_up,
                onClick = toggleFocusButtonClicked
            )

        }
    )
}