package com.jotte.core.audio

import com.jotte.core.datetime.CoroutineTimer
import io.github.vinceglb.filekit.PlatformFile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

actual class AudioRecorder actual constructor(_timer: CoroutineTimer) {

    actual val timer: CoroutineTimer = _timer
    actual val isRecording: Flow<Boolean> = MutableStateFlow(false)

    actual fun beginRecording() {}
    actual fun finishRecording(): Result<PlatformFile> {
        return Result.failure(RuntimeException())
    }
    actual fun cancelRecording() {}

}