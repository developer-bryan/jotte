package com.jotte.core.audio

import io.github.vinceglb.filekit.PlatformFile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

expect class AudioPlayer(scope: CoroutineScope) {

    val time: Flow<Long>
    val isPlaying: Flow<Boolean>

    fun setupPlayer(file: PlatformFile)

    fun play()

    fun stop()

    fun pause()

    fun dispose()
}
