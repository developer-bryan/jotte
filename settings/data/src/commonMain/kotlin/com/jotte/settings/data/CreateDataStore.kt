package com.jotte.settings.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

expect fun createDataStore(fileName: String): DataStore<Preferences>
