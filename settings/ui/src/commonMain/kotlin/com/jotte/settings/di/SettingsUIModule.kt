package com.jotte.settings.di

import com.jotte.settings.viewmodel.SettingsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

fun provideSettingsUIModule() =
    module {

        viewModel<SettingsViewModel> { SettingsViewModel(get()) }

    }