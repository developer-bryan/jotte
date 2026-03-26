package com.jotte.core.di

import com.jotte.core.permission.PermissionManager
import com.jotte.core.permission.PermissionManagerImpl
import com.jotte.core.usecase.DownloadMediaUseCase
import org.koin.core.qualifier.StringQualifier
import org.koin.dsl.module

fun urlRegex() = StringQualifier("namedUrlPattern")

fun phoneRegex() = StringQualifier("namedPhonePattern")

fun emailRegex() = StringQualifier("namedEmailPattern")

fun imageRegex() = StringQualifier("imageRegex")

fun provideCoreModule() =
    module {

        single<String>(
            qualifier = urlRegex(),
            definition = { """\b((https?|ftp)://)?(www\.)?[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}(/[^\s]*)?\b""" }
        )

        single<String>(
            qualifier = phoneRegex(),
            definition = { """(\+?\d{1,2}[- ]?)?\(?(\d{3})\)?[- ]?(\d{3})[- ]?(\d{4})""" }
        )

        single<String>(
            qualifier = emailRegex(),
            definition = { """^[A-Za-z0-9+_.-]+@[A-Za-z0-9-]+(\.[A-Za-z0-9-]+)+$""" }
        )

        single<Regex>(
            qualifier = imageRegex(),
            definition = { """\.(jpg|jpeg|png|heic|bmp|webp)$""".toRegex() }
        )

        single<PermissionManager> { PermissionManagerImpl() }
        single<DownloadMediaUseCase> { DownloadMediaUseCase(get(imageRegex())) }
    }