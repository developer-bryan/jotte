package com.jotte.room.screen.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jotte.cxui.component.CXScrollbar
import com.jotte.cxui.extension.ColumnExtension.lazySpace
import com.jotte.cxui.theme.sizes
import com.jotte.room.model.data.NoteActionsSheetParams
import com.jotte.room.model.state.NoteState
import com.jotte.room.screen.component.NoteComponent
import com.jotte.room.screen.component.NoteListQuickScrollButton
import com.jotte.room.screen.controller.rememberNoteController
import kotlinx.coroutines.launch

@Composable
internal fun NotesList(
    notes: List<NoteState>,
    modifier: Modifier = Modifier,
    isFullscreen: Boolean = false,
    onNoteLongPress: (params: NoteActionsSheetParams) -> Unit,
    onMediaClicked: (notestate: NoteState, mediaIndex: Int) -> Unit,
    onPlayAudioClicked: (audioId: String) -> Unit,
) {

    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    val showQuickScrollButton by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 1 }
    }

    LaunchedEffect(notes.size) {
        if (listState.firstVisibleItemIndex > 0) {
            listState.animateScrollToItem(0)
        }
    }

    CXScrollbar(listState) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            state = listState,
            contentPadding = PaddingValues(vertical = sizes.small),
            verticalArrangement = Arrangement.spacedBy(sizes.small),
            reverseLayout = true,
            content = {

                lazySpace { 98.dp }

                itemsIndexed(
                    items = notes,
                    key = { _, item -> item.noteId },
                    itemContent = { _, noteState ->
                        NoteComponent(
                            noteState = noteState,
                            controller = rememberNoteController(noteState),
                            onLongPress = onNoteLongPress,
                            onImageClicked = { onMediaClicked(noteState, it) },
                            onPlayAudioClicked = onPlayAudioClicked,
                        )
                    }
                )

                if (isFullscreen) {
                    lazySpace { huge }
                }
            }
        )

        NoteListQuickScrollButton(
            isVisible = !isFullscreen && showQuickScrollButton,
            modifier =
                Modifier
                    .padding(sizes.regular)
                    .align(Alignment.BottomCenter),
            onClick = { scope.launch { listState.animateScrollToItem(0) } }
        )

    }

}