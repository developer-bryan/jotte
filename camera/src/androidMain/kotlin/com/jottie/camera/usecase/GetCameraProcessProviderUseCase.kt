package com.jottie.camera.usecase

import android.content.Context
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.lifecycle.awaitInstance

internal class GetCameraProcessProviderUseCase(private val context: Context) {

    suspend operator fun invoke(): ProcessCameraProvider {
        return ProcessCameraProvider.awaitInstance(context)
    }

}