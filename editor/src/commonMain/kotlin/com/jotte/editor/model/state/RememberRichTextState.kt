package com.jotte.editor.model.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.text.style.TextDecoration
import com.jotte.cxui.theme.colors
import com.jotte.cxui.theme.sizes
import com.mohamedrejeb.richeditor.model.RichTextConfig
import com.mohamedrejeb.richeditor.model.RichTextState

@Composable
fun rememberRichTextState(configOptions: (RichTextConfig) -> Unit = {}): RichTextState {

    val color = colors
    val sizes = sizes
    val state =
        com.mohamedrejeb.richeditor.model
            .rememberRichTextState()

    LaunchedEffect(state) {
        state.config.linkColor = color.linkColor
        state.config.linkTextDecoration = TextDecoration.Underline
        state.config.listIndent = sizes.richTextListIndent
        state.config.preserveStyleOnEmptyLine = false
        configOptions(state.config)
    }

    return state
}