package com.jotte.settings.data.repository

import androidx.datastore.preferences.core.Preferences
import com.jotte.settings.data.model.AppAppearance
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    fun <T> readValue(
        key: Preferences.Key<T>,
        defaultValue: T?
    ): Flow<T?>

    suspend fun <T> putValue(
        key: Preferences.Key<T>,
        value: T
    )

    fun readAppAppearance(): Flow<AppAppearance?>

    fun readSoundEffects(): Flow<Boolean?>

}