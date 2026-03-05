package com.jottie.core.audio

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.Player.REPEAT_MODE_ONE
import androidx.media3.exoplayer.ExoPlayer
import com.jottie.core.ApplicationProvider
import io.github.vinceglb.filekit.AndroidFile
import io.github.vinceglb.filekit.PlatformFile
import kotlinx.coroutines.CoroutineScope
import java.io.FileNotFoundException

actual class AudioPlayer actual constructor(private val scope: CoroutineScope) {

    private val TAG = this::class.toString()

    private val _time: MutableState<Long> = mutableStateOf(0L)
    actual val time: State<Long> = _time

    private val _isPlaying: MutableState<Boolean> = mutableStateOf(false)
    actual val isPlaying: State<Boolean> = _isPlaying

    private var player: ExoPlayer? = null

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
        }

    }

    actual fun play() {

        player?.addListener(
            object : Player.Listener {
                override fun onPlaybackStateChanged(playbackState: Int) {
                    if (playbackState == Player.STATE_READY) {
                        val duration = player?.duration
                        if (duration != C.TIME_UNSET && duration != null) {
                            // playbackDurationListener(duration)
                        }
                    }
                }
            }
        )

        player?.prepare()
        player?.playWhenReady = true // or false if you want manual control
        _isPlaying.value = true
    }

    actual fun stop() {
        player?.stop()
        _isPlaying.value = false
    }

    actual fun pause() {
        player?.pause()
        _isPlaying.value = false
    }

    actual fun dispose() {
        player?.release()
        player = null
    }

}