package com.jotte.cxui.soundeffect

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class SoundEffectsDecorator(
    private val player: SoundEffectsPlayerImpl,
    private val soundEffectsEnabledFlag: Flow<Boolean?>,
    scope: CoroutineScope
) : SoundEffectsPlayer {

    private var soundEffectsEnabled: Boolean = true

    init {
        scope.launch {
            soundEffectsEnabledFlag.collect {
                this@SoundEffectsDecorator.soundEffectsEnabled = it ?: true
            }
        }
    }

    override fun playSound(soundEffect: SoundEffect) {
        if (soundEffectsEnabled) {
            player.playSound(soundEffect)
        }
    }

    override fun release() {
        player.release()
    }
}