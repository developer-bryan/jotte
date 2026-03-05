package com.jottie.audioplayer.di

import com.jottie.audioplayer.viewmodel.AudioNoteViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

fun provideAudioNoteModule() = module {

    viewModel<AudioNoteViewModel> {
        AudioNoteViewModel(get(), get(), get())
    }

}