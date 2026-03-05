package com.jottie.camera.di

import android.app.Application
import android.view.Surface
import androidx.camera.core.ImageCapture
import androidx.camera.core.MirrorMode.MIRROR_MODE_OFF
import androidx.camera.core.Preview
import com.jottie.core.ApplicationProvider
import com.jottie.camera.usecase.GetCameraProcessProviderUseCase
import com.jottie.camera.viewmodel.CameraViewModel
import com.jottie.core.permission.PermissionManagerImpl
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
            permissionManager = PermissionManagerImpl(),
        )
    }

}