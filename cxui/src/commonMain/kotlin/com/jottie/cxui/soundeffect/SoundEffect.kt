package com.jottie.cxui.soundeffect

import com.jottie.cxui.Res

sealed class SoundEffect(val uri: String) {
    data class Custom(val fileName: String) : SoundEffect(Res.getUri(fileName))
    data object SoundEffectCreation : SoundEffect(Res.getUri("files/effect_new_item.mp3"))
    data object SoundEffectLongPress : SoundEffect(Res.getUri("files/effect_long_press.mp3"))
    data object SoundEffectRemoval : SoundEffect(Res.getUri("files/swipe.mp3"))
    data object SoundEffectRecordingStarted : SoundEffect(Res.getUri("files/recording_started.mp3"))
}