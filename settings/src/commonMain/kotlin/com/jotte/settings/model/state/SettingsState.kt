package com.jotte.settings.model.state

internal data class SettingsState(
    val appearance: String = "system",
    val soundEffectsEnabled: Boolean = false
)
