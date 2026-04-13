package com.jotte.room.screen.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jotte.cxui.theme.typography
import com.mohamedrejeb.richeditor.model.RichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichText

@Composable
internal fun NoteContentComponent(
    state: RichTextState,
    modifier: Modifier = Modifier,
) {

    RichText(
        state = state,
        modifier = modifier,
        style = typography.bodyOne
    )

}