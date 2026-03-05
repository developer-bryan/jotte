package com.jottie.editor.model.state

import com.jottie.core.VirtualFile

internal data class DraftAudioState(
    val file: VirtualFile,
    val duration: Long,
    val title: String? = null
)