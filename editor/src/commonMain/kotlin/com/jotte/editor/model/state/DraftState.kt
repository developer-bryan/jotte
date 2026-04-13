package com.jotte.editor.model.state

import io.github.vinceglb.filekit.PlatformFile

internal class DraftState(
    val roomId: Long,
    val noteId: Long? = null,
    val canSubmit: Boolean = false,
    val audio: DraftAudioState? = null,
    val media: List<PlatformFile> = emptyList(),
    val links: List<DraftLinkState> = emptyList()
)