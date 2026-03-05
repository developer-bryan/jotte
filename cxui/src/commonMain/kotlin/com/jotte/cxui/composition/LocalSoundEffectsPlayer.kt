package com.jotte.cxui.composition

import androidx.compose.runtime.staticCompositionLocalOf
import com.jotte.cxui.soundeffect.SoundEffectsPlayer

val LocalSoundEffectPlayer = staticCompositionLocalOf<SoundEffectsPlayer?> { null }