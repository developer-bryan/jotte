package com.jotte.settings.model.event

internal sealed interface SettingsEvent {
    data object OnAppearanceUpdated: SettingsEvent
    data object OnSoundEffectsEnabled: SettingsEvent
    data object OnSoundEffectsDisabled: SettingsEvent
}