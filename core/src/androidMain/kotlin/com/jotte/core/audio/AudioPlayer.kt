package com.jotte.core.audio

import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.Player.REPEAT_MODE_ONE
import androidx.media3.exoplayer.ExoPlayer
import com.jotte.core.ApplicationProvider
import io.github.vinceglb.filekit.AndroidFile
import io.github.vinceglb.filekit.PlatformFile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.io.FileNotFoundException

actual class AudioPlayer actual constructor(private val scope: CoroutineScope) {

    private val _time: MutableStateFlow<Long> = MutableStateFlow(0L)
    actual val time: Flow<Long> = _time

    private val _isPlaying: MutableStateFlow<Boolean> = MutableStateFlow(false)
    actual val isPlaying: Flow<Boolean> = _isPlaying

    private var player: ExoPlayer? = null

    private val playerListener =
        object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                _isPlaying.value = isPlaying
                if (isPlaying) {
                    trackTime()
                }
            }
        }

    @Throws(FileNotFoundException::class, IllegalStateException::class)
    actual fun setupPlayer(file: PlatformFile) {

        val context = ApplicationProvider.getApplication()
        checkNotNull(context)

        val uri =
            when (val androidFile = file.androidFile) {
                is AndroidFile.FileWrapper -> {
                    val fileToCheck = androidFile.file
                    if (!fileToCheck.exists()) {
                        throw FileNotFoundException("file not found")
                    }
                    Uri.fromFile(fileToCheck)
                }

                is AndroidFile.UriWrapper -> androidFile.uri
            }

        player =
            ExoPlayer.Builder(context).build().apply {
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

    @Suppress("MagicNumber")
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