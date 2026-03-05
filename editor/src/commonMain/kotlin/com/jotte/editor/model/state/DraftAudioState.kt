package com.jotte.editor.model.state

import com.jotte.core.VirtualFile

internal data class DraftAudioState(
    val file: VirtualFile,
    val duration: Long,
    val title: String? = null
)