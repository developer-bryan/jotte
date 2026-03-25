package com.jotte.app.di

import com.jotte.app.usecase.MapRoomUseCase
import com.jotte.app.viewmodel.MainViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

fun provideMainModule() =
    module {

        viewModel<MainViewModel> {
            MainViewModel(
                createRoomUseCase = get(),
                renameRoomUseCase = get(),
                deleteRoomUseCase = get(),
                roomRepository = get(),
                mapRoomUseCase = get(),
                soundEffectsPlayer = get()
            )
        }

        factory<MapRoomUseCase> { MapRoomUseCase(get()) }
    }