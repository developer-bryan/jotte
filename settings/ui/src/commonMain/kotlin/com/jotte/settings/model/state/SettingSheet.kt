package com.jotte.settings.model.state

internal sealed interface SettingSheet {
    data object AppearanceSheet : SettingSheet

    data object SoundEffectSheet : SettingSheet
}