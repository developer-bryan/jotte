package com.jottie.core.audio

import com.jottie.core.VirtualFile
import com.jottie.core.datetime.CoroutineTimer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

actual class AudioRecorder actual constructor(_timer: CoroutineTimer) {

    actual val timer: CoroutineTimer = _timer
    actual val isRecording: Flow<Boolean> = MutableStateFlow(false)
    actual val hasActiveRecording: Flow<Boolean> = MutableStateFlow(false)

    actual fun beginRecording() {}
    actual fun pauseRecording() {}
    actual fun resumeRecording() {}
    actual fun stopRecording(): Result<VirtualFile> {
        return Result.failure(RuntimeException())
    }

}