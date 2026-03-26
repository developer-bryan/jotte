package com.jotte.whiteboard.di

import com.jotte.whiteboard.usecase.MapWhiteboardPathUseCase
import com.jotte.whiteboard.usecase.UpdateWhiteboardUseCase
import com.jotte.whiteboard.viewmodel.WhiteboardViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.StringQualifier
import org.koin.dsl.module

internal fun whiteboardDownloadFileName() = StringQualifier("whiteboardDownloadFileName")

fun provideWhiteboardModule() =
    module {

        single<String>(
            qualifier = whiteboardDownloadFileName(),
            definition = { "whiteboard.png" }
        )

        viewModel<WhiteboardViewModel> {
            WhiteboardViewModel(
                getWhiteboardUseCase = get(),
                updateWhiteboardUseCase = get(),
                mapWhiteboardPathUseCase = get(),
                downloadMediaUseCase = get(),
                downloadFileName = get(whiteboardDownloadFileName())
            )
        }

        factory<MapWhiteboardPathUseCase> { MapWhiteboardPathUseCase() }
        factory<UpdateWhiteboardUseCase> { UpdateWhiteboardUseCase(get()) }

    }