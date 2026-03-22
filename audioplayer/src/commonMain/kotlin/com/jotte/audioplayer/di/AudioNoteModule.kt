package com.jotte.audioplayer.di

import com.jotte.audioplayer.viewmodel.AudioNoteViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

fun provideAudioNoteModule() = module {

    viewModel<AudioNoteViewModel> {
        AudioNoteViewModel(get(), get(), get(), get())
    }

}