package com.jotte.editor.screen.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import com.jotte.cxui.component.CXText
import com.jotte.cxui.theme.colors
import com.jotte.cxui.theme.sizes
import com.jotte.cxui.theme.typography

// TODO: Migrate to :CXUI
@Composable
internal fun AudioNameInputForm(
    roomName: String,
    value: String,
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester = remember { FocusRequester() },
    onValueChanged: (newName: String) -> Unit
) {

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    BasicTextField(
        value = value,
        modifier = modifier
            .focusRequester(focusRequester)
            .padding(start = sizes.small),
        onValueChange = onValueChanged::invoke,
        cursorBrush = SolidColor(colors.contentPrimary),
        textStyle = typography.bodyOne.copy(color = colors.contentPrimary),
        decorationBox = { innerTextField ->
            if (value.isEmpty()) {
                CXText(
                    text = roomName,
                    alpha = 0.85F,
                    style = typography.bodyOne
                )
            }
            innerTextField()
        },
    )
}