package com.jotte.core.audio

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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

@Composable
fun rememberAudioPlayer(): AudioPlayer {
    val scope = rememberCoroutineScope()
    return remember { AudioPlayer(scope) }
}
