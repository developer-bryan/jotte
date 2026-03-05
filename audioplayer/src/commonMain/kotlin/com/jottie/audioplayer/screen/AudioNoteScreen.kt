package com.jottie.audioplayer.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.jottie.cxui.Res
import com.jottie.cxui.controller.rememberDialogController
import com.jottie.cxui.delete_draft_audio_dialog_body
import com.jottie.cxui.delete_draft_audio_dialog_title
import com.jottie.cxui.extension.ColumnExtension.FillSpace
import com.jottie.cxui.theme.sizes
import com.jottie.audioplayer.model.state.AudioScreenState
import com.jottie.audioplayer.model.state.rememberAudioFileSaver
import com.jottie.audioplayer.screen.component.AudioShutterButton
import com.jottie.audioplayer.screen.component.AudioTitle
import com.jottie.audioplayer.screen.layout.AudioPlayerToolbar
import com.jottie.audioplayer.screen.layout.AudioProgressBar
import com.jottie.audioplayer.screen.layout.ErrorLayout
import com.jottie.audioplayer.viewmodel.AudioNoteViewModel
import io.github.vinceglb.filekit.extension
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AudioNoteScreen(
    audioId: String,
    onCloseClicked: () -> Unit,
) {

    val viewModel: AudioNoteViewModel = koinViewModel()
    val screenState by viewModel.state.collectAsState(AudioScreenState.Nothing)
    val isPlaying by viewModel.player.isPlaying
    val runtime by viewModel.player.time

    LaunchedEffect(audioId) { viewModel.loadAudioNote(audioId) }

    val removeAudioDialogController = rememberDialogController<Unit>(
        title = Res.string.delete_draft_audio_dialog_title,
        body = Res.string.delete_draft_audio_dialog_body,
        onPositiveButtonClick = { viewModel.deleteAudioNote() }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = sizes.small),
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {

            when (val state = screenState) {
                AudioScreenState.Nothing -> Unit
                AudioScreenState.Error -> ErrorLayout()
                is AudioScreenState.Success -> {

                    val audioSaver = rememberAudioFileSaver(state.file)

                    AudioPlayerToolbar(
                        onCloseClicked = onCloseClicked,
                        onSaveClicked = {
                            audioSaver.launch(
                                suggestedName = state.title ?: "audio note",
                                extension = state.file.extension
                            )
                        },
                        onRemoveClicked = removeAudioDialogController::show
                    )

                    FillSpace()

                    AudioTitle(title = state.title)

                    AudioProgressBar(
                        current = runtime,
                        duration = state.duration,
                        modifier = Modifier
                            .padding(top = sizes.huge)
                            .padding(bottom = sizes.medium)
                            .padding(horizontal = sizes.regular)
                    )

                    AudioShutterButton(
                        isPlaying = isPlaying,
                        onClick = {
                            if (isPlaying) viewModel.pause() else viewModel.play()
                        }
                    )
                }
            }
        }
    )
}
