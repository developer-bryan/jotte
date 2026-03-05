package com.jottie.core.audio

import com.jottie.core.VirtualFile
import com.jottie.core.datetime.CoroutineTimer
import kotlinx.coroutines.flow.Flow

expect class AudioRecorder(_timer: CoroutineTimer) {

    val timer: CoroutineTimer
    val isRecording: Flow<Boolean>
    val hasActiveRecording: Flow<Boolean>

    fun beginRecording()
    fun pauseRecording()
    fun resumeRecording()
    fun stopRecording(): Result<VirtualFile>

}