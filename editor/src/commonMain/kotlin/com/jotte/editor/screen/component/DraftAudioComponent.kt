package com.jotte.editor.screen.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jotte.editor.model.state.DraftAudioState

@Composable
internal fun DraftAudioComponent(
    audio: DraftAudioState,
    modifier: Modifier = Modifier,
    onRenameClicked: () -> Unit,
    onSaveClicked: () -> Unit,
    onRemoveClicked: () -> Unit
) {



}