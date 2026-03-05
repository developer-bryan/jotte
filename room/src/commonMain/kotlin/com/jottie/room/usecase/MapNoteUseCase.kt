package com.jottie.room.usecase

import com.jottie.core.datetime.usecase.GetFullDateUseCase
import com.jottie.core.storageFile
import com.jottie.message.data.LinkDto
import com.jottie.message.data.FullNote
import com.jottie.room.model.state.NoteState
import io.github.vinceglb.filekit.FileKit

internal class MapNoteUseCase(
    private val getFullDateUseCase: GetFullDateUseCase,
    private val imageRegex: Regex,
) {

    suspend operator fun invoke(note: FullNote): NoteState {

        val noteMedia = note.media ?: emptyList()
        val links = ArrayList<NoteState.LinkState>()
        val createdOnFullDate = getFullDateUseCase(note.note.createdOn)
        val content = note.note.content?.let { NoteState.ContentState(it.value) }

        val audio = note.note.audio?.let {
            NoteState.AudioState(
                id = it.audioId,
                file = FileKit.storageFile(it.fileName),
                title = it.title,
                duration = it.duration
            )
        }

        val media = noteMedia.filter { it.fileName.contains(imageRegex) }

        note.links?.forEach {
            val link = it.toStateModel()
            links.add(link)
        }

        return NoteState(
            roomId = note.note.roomId,
            noteId = note.note.noteId,
            createdOnDate = createdOnFullDate.date,
            createdOnTime = createdOnFullDate.time,
            content = content,
            audio = audio,
            media = media,
            links = links
        )

    }

    private fun LinkDto.toStateModel() = NoteState.LinkState(
        id = linkId,
        type = linkType,
        link = link
    )

}