package com.jotte.core.link.di

import com.jotte.core.link.usecase.ValidateAppLinkUseCase
import org.koin.core.qualifier.StringQualifier
import org.koin.dsl.module

fun urlRegex() = StringQualifier("namedUrlPattern")
fun phoneRegex() = StringQualifier("namedPhonePattern")
fun emailRegex() = StringQualifier("namedEmailPattern")

fun provideLinkModule() =
    module {

        single<Regex>(
            qualifier = urlRegex(),
            definition = { """\b((https?|ftp)://)?(www\.)?[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}(/[^\s]*)?\b""".toRegex() }
        )

        single<Regex>(
            qualifier = phoneRegex(),
            definition = { """(\+?\d{1,2}[- ]?)?\(?(\d{3})\)?[- ]?(\d{3})[- ]?(\d{4})""".toRegex() }
        )

        single<Regex>(
            qualifier = emailRegex(),
            definition = { """^[A-Za-z0-9+_.-]+@[A-Za-z0-9-]+(\.[A-Za-z0-9-]+)+$""".toRegex() }
        )

        factory<ValidateAppLinkUseCase> {
            ValidateAppLinkUseCase(
                urlValidationRegex = get(urlRegex()),
                phoneValidationRegex = get(phoneRegex()),
                emailValidationRegex = get(emailRegex())
            )
        }

    }