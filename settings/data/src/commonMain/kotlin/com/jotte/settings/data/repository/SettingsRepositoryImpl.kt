package com.jotte.settings.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.jotte.settings.data.model.AppAppearance
import com.jotte.settings.data.model.AppearanceKey
import com.jotte.settings.data.model.SoundEffectsKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class SettingsRepositoryImpl(private val datastore: DataStore<Preferences>) : SettingsRepository {

    override fun <T> readValue(
        key: Preferences.Key<T>,
        defaultValue: T?
    ): Flow<T?> =
        datastore.data.map { preferences ->
            preferences[key] ?: defaultValue
        }

    override suspend fun <T> putValue(
        key: Preferences.Key<T>,
        value: T
    ) {
        datastore.edit { preferences ->
            preferences[key] = value
        }
    }

    override fun readAppAppearance(): Flow<AppAppearance?> =
        readValue(AppearanceKey, null).map { value -> value?.let(AppAppearance::valueOf) }

    override fun readSoundEffects(): Flow<Boolean?> = readValue(SoundEffectsKey, null)
}