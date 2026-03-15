package com.jotte.settings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jotte.settings.data.model.AppAppearance
import com.jotte.settings.data.model.AppearanceKey
import com.jotte.settings.data.repository.SettingsRepository
import com.jotte.settings.model.event.SettingsEvent
import com.jotte.settings.model.state.SettingsState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

internal class SettingsViewModel(private val repository: SettingsRepository) : ViewModel() {

    val event = Channel<SettingsEvent>(UNLIMITED)

    val settingsState = combine(
        flow = repository.readAppAppearance(),
        flow2 = repository.readSoundEffects(),
        transform = { appearance, soundEffects ->
            SettingsState(
                appearance = appearance ?: AppAppearance.SYSTEM,
                soundEffectsEnabled = soundEffects ?: true
            )
        }
    )

    fun updateAppAppearance(appAppearance: AppAppearance) {
        viewModelScope.launch {
            repository.putValue(
                key = AppearanceKey,
                value = appAppearance.name
            )
        }
    }

}