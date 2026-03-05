package com.jottie.room.screen.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.jottie.cxui.component.CXText
import com.jottie.cxui.theme.typography
import com.jottie.room.model.state.NoteState

@Composable
internal fun NoteContentComponent(
    modifier: Modifier = Modifier,
    content: NoteState.ContentState,
) {

    Box(
        modifier = modifier.fillMaxWidth(0.95F),
        contentAlignment = Alignment.CenterStart,
        content = {
            CXText(
                text = content.value,
                style = typography.bodyOne
            )
        }
    )

}