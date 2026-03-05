package com.jottie.audioplayer.model.state

import io.github.vinceglb.filekit.PlatformFile

sealed interface AudioScreenState {

    data class Success(
        val duration: Long,
        val title: String?,
        val file: PlatformFile
    ) : AudioScreenState

    data object Error : AudioScreenState
    data object Nothing: AudioScreenState
}