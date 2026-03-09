package com.jotte.whiteboard.di

import com.jotte.whiteboard.viewmodel.WhiteboardViewModel
import org.koin.dsl.module
import org.koin.core.module.dsl.viewModel

fun provideWhiteboardModule() = module {

    viewModel<WhiteboardViewModel> {
        WhiteboardViewModel()
    }

}