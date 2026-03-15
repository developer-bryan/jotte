package com.jotte.settings.model.state

import com.jotte.settings.data.model.AppAppearance

internal data class SettingsState(
    val appearance: AppAppearance = AppAppearance.SYSTEM,
    val soundEffectsEnabled: Boolean = false
)
