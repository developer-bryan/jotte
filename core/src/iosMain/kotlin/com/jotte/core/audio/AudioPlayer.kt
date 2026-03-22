@file:OptIn(BetaInteropApi::class, ExperimentalForeignApi::class)

package com.jotte.core.audio

import io.github.vinceglb.filekit.PlatformFile
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCObjectVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.io.files.FileNotFoundException
import platform.AVFAudio.AVAudioPlayer
import platform.Foundation.NSError
import platform.Foundation.NSFileManager

actual class AudioPlayer actual constructor(private val scope: CoroutineScope) {

    private val TAG = this::class.toString()

    private val _time: MutableStateFlow<Long> = MutableStateFlow(0L)
    actual val time: Flow<Long> = _time

    private val _isPlaying: MutableStateFlow<Boolean> = MutableStateFlow(false)
    actual val isPlaying: Flow<Boolean> = _isPlaying

    var player: AVAudioPlayer? = null

    @Throws(FileNotFoundException::class, RuntimeException::class)
    actual fun setupPlayer(file: PlatformFile) {
        val nsurl = file.nsUrl
        val path = nsurl.path() ?: throw FileNotFoundException("file not found")

        if (!NSFileManager.defaultManager.fileExistsAtPath(path)) {
            throw FileNotFoundException("file not found")
        }

        memScoped {
            val error = alloc<ObjCObjectVar<NSError?>>()

            player = AVAudioPlayer(nsurl, error.ptr).apply {
                meteringEnabled = true
                numberOfLoops = -1
                volume = 1.0F
            }

            if (player == null || error.value != null) {
                val errorMessage = error.value?.localizedDescription ?: ""
                throw RuntimeException("Failed to instantiate AVAudioPlayer: $errorMessage")
            }
        }

    }

    actual fun play() {
        if (player == null) return
        player?.let {
            it.prepareToPlay()
            it.play()

            _isPlaying.value = true
            trackTime()
        }
    }

    actual fun pause() {
        player?.pause()
        _isPlaying.value = false
    }

    actual fun dispose() {
        player?.stop()
        player = null
    }

    actual fun stop() {
        player?.stop()
        _isPlaying.value = false
    }

    private fun trackTime() {
        scope.launch {
            while (isActive && player?.isPlaying() == true) {
                val time = player?.currentTime()
                _time.value = time?.toLong()?.times(1000) ?: 0L
                delay(100L)
            }
        }
    }

}
