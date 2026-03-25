package com.jotte.room.screen.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.jotte.cxui.component.CXText
import com.jotte.cxui.theme.sizes
import com.jotte.cxui.theme.typography
import com.jotte.room.model.state.NoteState

@Composable
internal fun NoteContentComponent(
    modifier: Modifier = Modifier,
    content: NoteState.ContentState,
) {

    Box(
        modifier = modifier.fillMaxWidth(sizes.noteContentMaxWidthPercentage),
        contentAlignment = Alignment.CenterStart,
        content = {
            CXText(
                text = content.value,
                style = typography.bodyOne
            )
        }
    )

}