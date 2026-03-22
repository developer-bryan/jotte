package com.jotte.core.audio

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.Player.REPEAT_MODE_ONE
import androidx.media3.exoplayer.ExoPlayer
import com.jotte.core.ApplicationProvider
import io.github.vinceglb.filekit.AndroidFile
import io.github.vinceglb.filekit.PlatformFile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.io.FileNotFoundException

actual class AudioPlayer actual constructor(private val scope: CoroutineScope) {

    private val TAG = this::class.toString()

    private val _time: MutableState<Long> = mutableLongStateOf(0L)
    actual val time: State<Long> = _time

    private val _isPlaying: MutableState<Boolean> = mutableStateOf(false)
    actual val isPlaying: State<Boolean> = _isPlaying

    private var player: ExoPlayer? = null

    private val playerListener = object: Player.Listener {
        override fun onIsPlayingChanged(isPlaying: Boolean) {
            _isPlaying.value = isPlaying
            if (isPlaying) {
                trackTime()
            }
        }
    }

    @Throws(FileNotFoundException::class)
    actual fun setupPlayer(file: PlatformFile) {

        val context =
            ApplicationProvider.getApplication() ?: throw IllegalStateException("missing context")

        val uri = when (val androidFile = file.androidFile) {
            is AndroidFile.FileWrapper -> {
                val fileToCheck = androidFile.file
                if (!fileToCheck.exists()) {
                    throw FileNotFoundException("file not found")
                }
                Uri.fromFile(fileToCheck)
            }

            is AndroidFile.UriWrapper -> androidFile.uri
        }

        player = ExoPlayer.Builder(context).build().apply {
            val mediaItem = MediaItem.fromUri(uri)

            setMediaItem(mediaItem)
            repeatMode = REPEAT_MODE_ONE
            playWhenReady = true

            addListener(playerListener)
        }

    }

    actual fun play() {
        player?.prepare()
        player?.play()
    }

    actual fun stop() {
        player?.stop()
    }

    actual fun pause() {
        player?.pause()
    }

    actual fun dispose() {
        player?.release()
        player = null
    }

    private fun trackTime() {
        scope.launch {
            while (isActive && _isPlaying.value) {
                val time = player?.currentPosition
                _time.value = ((time ?: 0L) / 1000) * 1000
                delay(100L)
            }
        }
    }

}