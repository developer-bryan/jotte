package com.jotte.editor.model.state

import io.github.vinceglb.filekit.PlatformFile

internal data class DraftAudioState(
    val file: PlatformFile,
    val duration: Long,
    val title: String? = null
)