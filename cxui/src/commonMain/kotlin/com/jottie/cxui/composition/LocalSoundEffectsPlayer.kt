package com.jottie.cxui.composition

import androidx.compose.runtime.staticCompositionLocalOf
import com.jottie.cxui.soundeffect.SoundEffectsPlayer

val LocalSoundEffectPlayer = staticCompositionLocalOf<SoundEffectsPlayer?> { null }