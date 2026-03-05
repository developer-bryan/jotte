package com.jotte.cxui.soundeffect

expect class SoundEffectsPlayer() {
    fun playSound(soundEffect: SoundEffect)
    fun release()
}