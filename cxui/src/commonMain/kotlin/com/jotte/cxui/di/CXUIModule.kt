package com.jotte.cxui.di

import com.jotte.cxui.soundeffect.SoundEffectsDecorator
import com.jotte.cxui.soundeffect.SoundEffectsPlayer
import com.jotte.cxui.soundeffect.SoundEffectsPlayerImpl
import com.jotte.settings.data.repository.SettingsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import org.koin.dsl.module
import org.koin.dsl.onClose

fun provideCXUIModule() = module {

    single<SoundEffectsPlayer> {

        val player = SoundEffectsPlayerImpl()
        val flow = get<SettingsRepository>().readSoundEffects()

        SoundEffectsDecorator(
            player = player,
            soundEffectsEnabledFlag = flow,
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
        )

    }.onClose { it?.release() }

}