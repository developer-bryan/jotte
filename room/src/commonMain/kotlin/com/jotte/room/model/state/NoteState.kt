package com.jotte.room.model.state

import com.jotte.data.persistence.data.LinkDto
import com.jotte.data.persistence.data.MediaDto
import io.github.vinceglb.filekit.PlatformFile

internal class NoteState(
    val roomId: Long,
    val noteId: Long,
    val createdOnDate: String,
    val createdOnTime: String,
    val content: ContentState? = null,
    val audio: AudioState? = null,
    val media: List<MediaDto>,
    val links: List<LinkState>
) {

    internal data class ContentState(val value: String)

    internal data class AudioState(
        val id: String,
        val file: PlatformFile,
        val duration: Long,
        val title: String? = null
    )

    internal data class LinkState(
        val id: String,
        val type: LinkDto.LinkType,
        val link: String
    )

}
