package com.jotte.room.screen.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import com.jotte.cxui.theme.typography
import com.jotte.room.model.state.rememberRichTextUriHandler
import com.mohamedrejeb.richeditor.model.RichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichText

@Composable
internal fun NoteContentComponent(
    state: RichTextState,
    modifier: Modifier = Modifier,
    uriHandler: UriHandler = rememberRichTextUriHandler()
) {
    CompositionLocalProvider(
        value = LocalUriHandler.provides(uriHandler),
        content = {
            RichText(
                state = state,
                modifier = modifier,
                style = typography.bodyOne
            )
        }
    )
}