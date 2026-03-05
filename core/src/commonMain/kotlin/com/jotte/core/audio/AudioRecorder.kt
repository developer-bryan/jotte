package com.jotte.core.audio

import com.jotte.core.VirtualFile
import com.jotte.core.datetime.CoroutineTimer
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