package com.jotte.room.di

import com.jotte.core.di.imageRegex
import com.jotte.room.usecase.MapNoteUseCase
import com.jotte.room.viewmodel.RoomViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

fun provideRoomModule() =
    module {

        viewModel<RoomViewModel> {
            RoomViewModel(
                roomId = get(),
                roomRepository = get(),
                noteRepository = get(),
                mapNoteUseCase = get(),
                getFullDateUseCase = get(),
                deleteNoteUseCase = get(),
                deleteMediaUseCase = get(),
                saveFileToGalleryUseCase = get(),
                soundEffectsPlayer = get()
            )
        }

        factory<MapNoteUseCase> {
            MapNoteUseCase(
                getFullDateUseCase = get(),
                imageRegex = get(imageRegex())
            )
        }
    }