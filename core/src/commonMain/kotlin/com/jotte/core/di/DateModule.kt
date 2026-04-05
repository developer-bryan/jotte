package com.jotte.core.di

import com.jotte.core.datetime.DateTimeStrings
import com.jotte.core.datetime.usecase.GetFullDateUseCase
import org.koin.dsl.module

fun provideDateModule(dateTimeStrings: DateTimeStrings) =
    module {
        single<DateTimeStrings> { dateTimeStrings }
        factory<GetFullDateUseCase> { GetFullDateUseCase(get()) }
    }