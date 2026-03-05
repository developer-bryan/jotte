package com.jotte.core.di

import com.jotte.core.datetime.DateTimeStrings
import com.jotte.core.datetime.usecase.GetDateUseCase
import com.jotte.core.datetime.usecase.GetFullDateUseCase
import com.jotte.core.datetime.usecase.GetMonthYearUseCase
import org.koin.dsl.module

fun provideDateModule(dateTimeStrings: DateTimeStrings) = module {
    single<DateTimeStrings> { dateTimeStrings }
    factory<GetDateUseCase> { GetDateUseCase(get()) }
    factory<GetMonthYearUseCase> { GetMonthYearUseCase(get()) }
    factory<GetFullDateUseCase> { GetFullDateUseCase(get()) }
}