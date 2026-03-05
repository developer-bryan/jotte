package com.jottie.editor.model.state

import com.jottie.core.VirtualFile
import com.jottie.core.storageFile
import com.jottie.message.data.MediaDto
import io.github.vinceglb.filekit.FileKit

internal class DraftState(
    val roomId: Long,
    val noteId: Long? = null,
    val isExistingNote: Boolean = false,
    val canSubmit: Boolean = false,
    val content: DraftContentState? = null,
    val audio: DraftAudioState? = null,
    val media: List<VirtualFile> = emptyList(),
    val links: List<DraftLinkState> = emptyList()
)

fun MediaDto.asPlatformFile() = FileKit.storageFile(fileName)
fun MediaDto.getExtension() = fileName.substringAfter('.')