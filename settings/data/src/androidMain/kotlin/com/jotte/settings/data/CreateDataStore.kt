package com.jotte.settings.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath

actual fun createDataStore(fileName: String): DataStore<Preferences> {
    val path = (SettingsApplicationProvider.getApplication() as Context)
        .filesDir.resolve(fileName).absolutePath.toPath()
    return PreferenceDataStoreFactory.createWithPath(produceFile = { path })
}