@file:OptIn(ExperimentalForeignApi::class)

package com.jotte.cxui.soundeffect

import kotlinx.cinterop.ExperimentalForeignApi
import platform.AVFAudio.AVAudioPlayer
import platform.Foundation.NSURL

actual class SoundEffectsPlayer {

    private val players = HashMap<NSURL, AVAudioPlayer>()

    actual fun playSound(soundEffect: SoundEffect) {
        try {
            NSURL.URLWithString(URLString = soundEffect.uri)?.let { media ->

                val player = if (players.containsKey(media)) {
                    players[media]
                } else {
                    val player = AVAudioPlayer(media, error = null)
                    players[media] = player
                    player
                }

                player?.prepareToPlay()
                player?.play()
            }
        } catch (exception: Exception) {
            println(exception)
        }
    }

    actual fun release() {
        players.values.forEach { it.stop() }
        players.clear()
    }
}
