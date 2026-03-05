package com.jotte.editor.di

import com.jotte.editor.usecase.CreateNoteUseCase
import com.jotte.editor.usecase.UpdateNoteUseCase
import com.jotte.editor.viewmodel.EditorViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

fun provideEditorModule() = module {

    viewModel<EditorViewModel> {
        EditorViewModel(
            getNoteUseCase = get(),
            createNoteUseCase = get(),
            updateNoteUseCase = get(),
            soundEffectsPlayer = get(),
            roomId = get(),
            noteId = get()
        )
    }

    factory<CreateNoteUseCase> { CreateNoteUseCase(get(), get()) }
    factory<UpdateNoteUseCase> { UpdateNoteUseCase(get(), get(), get()) }
}