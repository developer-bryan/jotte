package com.jottie.cxui.soundeffect

expect class SoundEffectsPlayer() {
    fun playSound(soundEffect: SoundEffect)
    fun release()
}