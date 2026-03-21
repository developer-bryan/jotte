package com.jotte.audioplayer.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.jotte.audioplayer.model.event.AudioPlayerEvent
import com.jotte.cxui.Res
import com.jotte.cxui.controller.rememberDialogController
import com.jotte.cxui.delete_draft_audio_dialog_body
import com.jotte.cxui.delete_draft_audio_dialog_title
import com.jotte.cxui.extension.ColumnExtension.FillSpace
import com.jotte.cxui.theme.sizes
import com.jotte.audioplayer.model.state.AudioScreenState
import com.jotte.audioplayer.screen.component.AudioShutterButton
import com.jotte.audioplayer.screen.component.AudioTitle
import com.jotte.audioplayer.screen.layout.AudioPlayerToolbar
import com.jotte.audioplayer.screen.layout.AudioProgressBar
import com.jotte.audioplayer.screen.layout.ErrorLayout
import com.jotte.audioplayer.viewmodel.AudioNoteViewModel
import com.jotte.core.rememberFileSaverPicker
import com.jotte.cxui.audio_deleted
import com.jotte.cxui.composition.LocalToastController
import com.jotte.cxui.extension.asEffect
import com.jotte.cxui.media_download
import io.github.vinceglb.filekit.extension
import io.github.vinceglb.filekit.nameWithoutExtension
import io.github.vinceglb.filekit.path
import kotlinx.coroutines.flow.consumeAsFlow
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun AudioNoteScreen(
    audioId: String,
    onCloseClicked: () -> Unit,
) {

    val toastController = LocalToastController.current
    val viewModel: AudioNoteViewModel = koinViewModel { parametersOf(audioId) }
    val screenState by viewModel.state.collectAsState(AudioScreenState.Nothing)
    val isPlaying by viewModel.player.isPlaying
    val runtime by viewModel.player.time

    val removeAudioDialogController = rememberDialogController<Unit>(
        title = Res.string.delete_draft_audio_dialog_title,
        body = Res.string.delete_draft_audio_dialog_body,
        onPositiveButtonClick = { viewModel.deleteAudioNote() }
    )

    viewModel.event.consumeAsFlow().asEffect { event ->
        when (event) {
            AudioPlayerEvent.OnAudioRemoved -> {
                toastController.show(Res.string.audio_deleted)
                onCloseClicked()
            }

            AudioPlayerEvent.OnError -> toastController.showError()
        }
    }

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

                    val audioSaver = rememberFileSaverPicker(
                        src = state.file,
                        onSuccess = { toastController.show(Res.string.media_download) },
                        onFailure = { _, _ -> toastController.showError() }
                    )

                    AudioPlayerToolbar(
                        onCloseClicked = onCloseClicked,
                        onSaveClicked = {
                            audioSaver.launch(
                                suggestedName = state.title ?: state.file.nameWithoutExtension,
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
