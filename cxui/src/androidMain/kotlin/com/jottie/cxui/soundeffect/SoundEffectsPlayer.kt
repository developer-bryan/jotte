package com.jottie.cxui.soundeffect

import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.jottie.core.ApplicationProvider

actual class SoundEffectsPlayer {

    private var mediaPlayer: ExoPlayer? = null

    init {
        ApplicationProvider.getApplication()?.let { ctx ->
            mediaPlayer = ExoPlayer.Builder(ctx).build()
            mediaPlayer?.prepare()
        }
    }

    actual fun playSound(soundEffect: SoundEffect) {
        val media = MediaItem.fromUri(soundEffect.uri)
        mediaPlayer?.setMediaItem(media)
        mediaPlayer?.prepare()
        mediaPlayer?.play()
    }

    actual fun release() {
        mediaPlayer?.release()
    }
}