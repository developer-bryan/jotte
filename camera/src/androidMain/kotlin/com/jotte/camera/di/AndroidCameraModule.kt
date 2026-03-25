package com.jotte.camera.di

import android.app.Application
import android.view.Surface
import androidx.camera.core.ImageCapture
import androidx.camera.core.MirrorMode.MIRROR_MODE_OFF
import androidx.camera.core.Preview
import com.jotte.core.ApplicationProvider
import com.jotte.camera.usecase.GetCameraProcessProviderUseCase
import com.jotte.camera.viewmodel.CameraViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

internal fun provideAndroidCameraModule() = module {

    factory<GetCameraProcessProviderUseCase> { GetCameraProcessProviderUseCase(get<Application>()) }

    factory<Application> { ApplicationProvider.getApplication()!! }

    factory<Preview> {
        Preview
            .Builder()
            .setMirrorMode(MIRROR_MODE_OFF)
            .setTargetRotation(Surface.ROTATION_0)
            .build()
    }

    factory<ImageCapture> { ImageCapture.Builder().build() }

    viewModel<CameraViewModel> {
        CameraViewModel(
            application = ApplicationProvider.getApplication()!!,
            getCameraProcessProviderUseCase = get(),
            preview = get(),
            imageCapture = get(),
        )
    }

}