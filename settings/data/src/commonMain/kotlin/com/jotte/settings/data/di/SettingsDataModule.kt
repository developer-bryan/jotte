package com.jotte.settings.data.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.jotte.settings.data.createDataStore
import com.jotte.settings.data.repository.SettingsRepository
import com.jotte.settings.data.repository.SettingsRepositoryImpl
import org.koin.core.qualifier.StringQualifier
import org.koin.dsl.module

fun settingsFileName() = StringQualifier("settingsFileName")

fun provideSettingsDataModule() =
    module {

        single<String>(
            qualifier = settingsFileName(),
            definition = { "jotte_settings.preferences_pb" }
        )

        single<DataStore<Preferences>> {
            createDataStore(get<String>(settingsFileName()))
        }

        factory<SettingsRepository> { SettingsRepositoryImpl(get()) }

    }