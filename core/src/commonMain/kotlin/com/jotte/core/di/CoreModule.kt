package com.jotte.core.di

import com.jotte.core.permission.PermissionManager
import com.jotte.core.permission.PermissionManagerImpl
import com.jotte.core.usecase.SaveFileToGalleryUseCase
import org.koin.core.qualifier.StringQualifier
import org.koin.dsl.module

fun imageRegex() = StringQualifier("imageRegex")

fun provideCoreModule() =
    module {

        single<Regex>(
            qualifier = imageRegex(),
            definition = { """\.(jpg|jpeg|png|heic|bmp|webp)$""".toRegex() }
        )

        single<PermissionManager> { PermissionManagerImpl() }
        single<SaveFileToGalleryUseCase> { SaveFileToGalleryUseCase(get(imageRegex())) }
    }