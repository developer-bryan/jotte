package com.jottie.editor.controller

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.jottie.cxui.soundeffect.SoundEffectsPlayer
import com.jottie.core.VirtualFile
import com.jottie.core.audio.AudioRecorder
import com.jottie.core.datetime.CoroutineTimer
import com.jottie.core.datetime.toFormattedRuntime
import com.jottie.cxui.Res
import com.jottie.cxui.controller.CXToastController
import com.jottie.cxui.composition.LocalToastController
import com.jottie.cxui.generic_error_message
import com.jottie.cxui.soundeffect.SoundEffect
import com.jottie.cxui.tap_to_record
import com.jottie.cxui.tap_to_stop
import kotlinx.coroutines.flow.map
import org.koin.compose.koinInject

internal class RecordAudioController(
    private val recorder: AudioRecorder,
    private val toastState: CXToastController,
    private val soundEffectsPlayer: SoundEffectsPlayer
) {

    val shouldShowCloseDialog = mutableStateOf(false)
    val shouldRequestAudioPermission = mutableStateOf(true)
    val shouldLaunchSettingsScreen = mutableStateOf(false)
    var hasAudioPermission by mutableStateOf<Boolean?>(null)

    val isRecording = recorder.isRecording
    val hasActiveRecording = recorder.hasActiveRecording
    val durationMillis = recorder.timer.rawElapsed
    val durationConverted = recorder.timer.convertedElapsed

    val captureLabel =
        isRecording.map { isRecording -> if (isRecording) Res.string.tap_to_stop else Res.string.tap_to_record }

    fun showCloseDialog() {
        shouldShowCloseDialog.value = true
    }

    fun beginRecording() {
        soundEffectsPlayer.playSound(SoundEffect.SoundEffectRecordingStarted)
        runCatching { recorder.beginRecording() }
            .onFailure { toastState.show(Res.string.generic_error_message) }
    }

    fun stopRecording(callback: (file: VirtualFile, runtime: Long) -> Unit) {
        recorder.stopRecording()
            .onSuccess { callback(it, durationMillis.value) }
            .onFailure { toastState.show(Res.string.generic_error_message) }
    }

    fun setHasAudioPermission() {
        hasAudioPermission = true
        shouldLaunchSettingsScreen.value = false
    }

    fun setNoAudioPermission() {
        hasAudioPermission = false
    }

    fun launchSettingsScreen() {
        shouldLaunchSettingsScreen.value = true
    }

}

@Composable
internal fun rememberRecordAudioController(): RecordAudioController {

    val soundEffectsPlayer: SoundEffectsPlayer = koinInject()
    val scope = rememberCoroutineScope()
    val timer = remember { CoroutineTimer(scope) { it.toFormattedRuntime() } }
    val recorder = remember { AudioRecorder(timer) }
    val toastState = LocalToastController.current

    DisposableEffect(Unit) { onDispose { recorder.stopRecording() } }

    return remember(recorder) {
        RecordAudioController(
            recorder = recorder,
            toastState = toastState,
            soundEffectsPlayer = soundEffectsPlayer
        )
    }
}
