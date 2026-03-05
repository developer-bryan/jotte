package com.jottie.cxui.di

import com.jottie.cxui.soundeffect.SoundEffectsPlayer
import org.koin.dsl.module
import org.koin.dsl.onClose

fun provideCXUIModule() = module {

    single<SoundEffectsPlayer> { SoundEffectsPlayer() }.onClose { it?.release() }

}