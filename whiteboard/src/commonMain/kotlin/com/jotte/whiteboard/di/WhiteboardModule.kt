package com.jotte.whiteboard.di

import com.jotte.whiteboard.viewmodel.WhiteboardViewModel
import org.koin.dsl.module
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.StringQualifier

internal fun whiteboardDownloadFileName() = StringQualifier("whiteboardDownloadFileName")

fun provideWhiteboardModule() = module {

    single<String>(
        qualifier = whiteboardDownloadFileName(),
        definition = { "whiteboard.png" }
    )

    viewModel<WhiteboardViewModel> {
        WhiteboardViewModel(
            downloadMediaUseCase = get(),
            downloadFileName = get(whiteboardDownloadFileName())
        )
    }

}