package com.jotte.cxui.soundeffect

interface SoundEffectsPlayer {
    fun playSound(soundEffect: SoundEffect)
    fun release()
}