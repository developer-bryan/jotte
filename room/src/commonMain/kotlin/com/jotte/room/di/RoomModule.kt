package com.jotte.room.di

import com.jotte.core.di.imageRegex
import com.jotte.room.usecase.MapNoteUseCase
import com.jotte.room.viewmodel.NoteViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

fun provideRoomModule() = module {

    viewModel<NoteViewModel> {
        NoteViewModel(
            roomId = get(),
            roomRepository = get(),
            noteRepository = get(),
            mapNoteUseCase = get(),
            getFullDateUseCase = get(),
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