package com.jotte.cxui.soundeffect

expect class SoundEffectsPlayerImpl(): SoundEffectsPlayer {
    override fun playSound(soundEffect: SoundEffect)
    override fun release()
}