package com.jotte.settings.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

expect fun createDataStore(
    settingsContextProvider: SettingsContextProvider,
    fileName: String
): DataStore<Preferences>
