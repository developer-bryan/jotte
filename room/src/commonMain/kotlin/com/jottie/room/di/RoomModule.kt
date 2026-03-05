package com.jottie.room.di

import com.jottie.core.di.imageRegex
import com.jottie.room.usecase.MapNoteUseCase
import com.jottie.room.viewmodel.NoteViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

fun provideRoomModule() = module {

    viewModel<NoteViewModel> {
        NoteViewModel(
            roomId = get(),
            roomRepository = get(),
            noteRepository = get(),
            mapNoteUseCase = get(),
            deleteNoteUseCase = get(),
            deleteMediaUseCase = get(),
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