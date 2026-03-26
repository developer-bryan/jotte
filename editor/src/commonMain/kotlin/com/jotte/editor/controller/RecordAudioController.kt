package com.jotte.editor.controller

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.jotte.core.audio.AudioRecorder
import com.jotte.core.datetime.CoroutineTimer
import com.jotte.core.datetime.toFormattedRuntime
import com.jotte.core.permission.Permission
import com.jotte.core.permission.rememberPermission
import com.jotte.cxui.Res
import com.jotte.cxui.composition.LocalToastController
import com.jotte.cxui.controller.CXToastController
import com.jotte.cxui.generic_error_message
import com.jotte.cxui.missing_audio_permission
import com.jotte.cxui.soundeffect.SoundEffect
import com.jotte.cxui.soundeffect.SoundEffectsPlayer
import io.github.vinceglb.filekit.PlatformFile
import org.koin.compose.koinInject

internal class RecordAudioController(
    private val recorder: AudioRecorder,
    private val toastController: CXToastController,
    private val soundEffectsPlayer: SoundEffectsPlayer,
    private val onRecordingFinished: (file: PlatformFile, duration: Long) -> Unit
) {

    val shouldRequestAudioPermission = mutableStateOf(false)
    val isRecording = recorder.isRecording

    val durationMillis = recorder.timer.elapsedTime
    val durationConverted = recorder.timer.elapsedTimeConverted

    var isAudioRecorderOpen by mutableStateOf(false)

    fun beginRecording() {
        soundEffectsPlayer.playSound(SoundEffect.SoundEffectRecordingStarted)
        runCatching { recorder.beginRecording() }
            .onFailure { toastController.show(Res.string.generic_error_message) }
    }

    fun finishRecording() {
        recorder
            .finishRecording()
            .onSuccess {
                onRecordingFinished(it, durationMillis.value)
                closeAudioRecorder()
            }.onFailure { toastController.show(Res.string.generic_error_message) }
    }

    fun checkAudioPermission() {
        this.shouldRequestAudioPermission.value = true
    }

    fun openAudioRecorder() {
        this.isAudioRecorderOpen = true
    }

    fun closeAudioRecorder() {
        this.isAudioRecorderOpen = false
    }

    fun cancelRecording() {
        recorder.cancelRecording()
        isAudioRecorderOpen = false
    }

}

@Composable
internal fun rememberRecordAudioController(
    onRecordingFinished: (file: PlatformFile, duration: Long) -> Unit
): RecordAudioController {

    val soundEffectsPlayer: SoundEffectsPlayer = koinInject()
    val scope = rememberCoroutineScope()
    val timer = remember { CoroutineTimer(scope) { it.toFormattedRuntime() } }
    val recorder = remember { AudioRecorder(timer) }
    val toastController = LocalToastController.current

    DisposableEffect(Unit) { onDispose { recorder.finishRecording() } }

    val controller =
        remember(recorder) {
            RecordAudioController(
                recorder = recorder,
                toastController = toastController,
                soundEffectsPlayer = soundEffectsPlayer,
                onRecordingFinished = onRecordingFinished
            )
        }

    val rememberAudioPermission =
        rememberPermission(
            shouldCheckPermission = controller.shouldRequestAudioPermission,
            permission = Permission.Audio,
            onPermissionGranted = controller::openAudioRecorder,
            onPermissionDenied = { toastController.showError(Res.string.missing_audio_permission) }
        )

    return controller
}
