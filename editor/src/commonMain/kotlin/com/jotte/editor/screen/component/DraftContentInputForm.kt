package com.jotte.editor.screen.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import com.jotte.cxui.Res
import com.jotte.cxui.component.CXText
import com.jotte.cxui.draft_hint
import com.jotte.cxui.theme.colors
import com.jotte.cxui.theme.sizes
import com.jotte.cxui.theme.typography
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun DraftContentInputForm(
    message: String,
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester = remember { FocusRequester() },
    onMessageChanged: (newMessage: String) -> Unit
) {

    BasicTextField(
        value = message,
        modifier =
            modifier
                .focusRequester(focusRequester)
                .padding(start = sizes.small),
        onValueChange = onMessageChanged::invoke,
        cursorBrush = SolidColor(colors.contentPrimary),
        textStyle = typography.bodyOne.copy(color = colors.contentPrimary),
        decorationBox = { innerTextField ->
            if (message.isEmpty()) {
                CXText(
                    text = stringResource(Res.string.draft_hint),
                    alpha = 0.85F,
                    style = typography.bodyOne
                )
            }
            innerTextField()
        },
    )
}
