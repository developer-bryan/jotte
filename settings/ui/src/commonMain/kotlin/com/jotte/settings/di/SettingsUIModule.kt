package com.jotte.settings.di

import com.jotte.settings.viewmodel.SettingsViewModel
import org.koin.dsl.module
import org.koin.core.module.dsl.viewModel

fun provideSettingsUIModule() = module {

    viewModel<SettingsViewModel> { SettingsViewModel(get()) }

}