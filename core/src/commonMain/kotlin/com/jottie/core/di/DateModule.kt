package com.jottie.core.di

import com.jottie.core.datetime.DateTimeStrings
import com.jottie.core.datetime.usecase.GetDateUseCase
import com.jottie.core.datetime.usecase.GetFullDateUseCase
import com.jottie.core.datetime.usecase.GetMonthYearUseCase
import org.koin.dsl.module

fun provideDateModule(dateTimeStrings: DateTimeStrings) = module {
    single<DateTimeStrings> { dateTimeStrings }
    factory<GetDateUseCase> { GetDateUseCase(get()) }
    factory<GetMonthYearUseCase> { GetMonthYearUseCase(get()) }
    factory<GetFullDateUseCase> { GetFullDateUseCase(get()) }
}