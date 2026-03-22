package com.jotte.core.audio

import androidx.compose.runtime.State
import io.github.vinceglb.filekit.PlatformFile
import kotlinx.coroutines.CoroutineScope

expect class AudioPlayer(scope: CoroutineScope) {

    val time: State<Long>
    val isPlaying: State<Boolean>

    fun setupPlayer(file: PlatformFile)
    fun play()
    fun stop()
    fun pause()

    fun dispose()
}
