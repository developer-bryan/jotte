@file:Suppress("ConstructorParameterNaming")

package com.jotte.core.audio

import com.jotte.core.datetime.CoroutineTimer
import io.github.vinceglb.filekit.PlatformFile
import kotlinx.coroutines.flow.Flow

expect class AudioRecorder(_timer: CoroutineTimer) {

    val timer: CoroutineTimer
    val isRecording: Flow<Boolean>

    fun beginRecording()

    fun finishRecording(): Result<PlatformFile>

    fun cancelRecording()

}