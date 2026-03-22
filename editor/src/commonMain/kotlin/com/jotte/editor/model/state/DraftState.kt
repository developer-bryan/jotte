package com.jotte.editor.model.state

import com.jotte.core.VirtualFile
import com.jotte.core.storageFile
import com.jotte.message.data.MediaDto
import io.github.vinceglb.filekit.FileKit

internal class DraftState(
    val roomId: Long,
    val noteId: Long? = null,
    val canSubmit: Boolean = false,
    val content: DraftContentState? = null,
    val audio: DraftAudioState? = null,
    val media: List<VirtualFile> = emptyList(),
    val links: List<DraftLinkState> = emptyList()
)

fun MediaDto.asPlatformFile() = FileKit.storageFile(fileName)
fun MediaDto.getExtension() = fileName.substringAfter('.')