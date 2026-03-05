@file:OptIn(ExperimentalTime::class)

package com.jottie.editor.usecase

import com.jottie.core.copyCacheToStorage
import com.jottie.editor.model.state.DraftState
import com.jottie.message.data.MediaDto
import com.jottie.message.data.LinkDto
import com.jottie.message.data.NoteDto
import com.jottie.message.repository.NoteRepository
import com.jottie.message.repository.RoomRepository
import io.github.vinceglb.filekit.FileKit
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

internal class CreateNoteUseCase(
    private val noteRepository: NoteRepository,
    private val roomRepository: RoomRepository
) {

    suspend operator fun invoke(draft: DraftState) {
        val createdOn = Clock.System.now().toEpochMilliseconds()
        val files = ArrayList<MediaDto>()
        val links = ArrayList<LinkDto>()

        draft.media.map {
            FileKit.copyCacheToStorage(it.fileName)
            val file = MediaDto(fileName = it.fileName)
            files.add(file)
        }

        draft.links.map {
            val link = LinkDto(
                link = it.link,
                linkType = it.type
            )
            links.add(link)
        }

        val content = draft.content?.let { NoteDto.Content(it.value) }
        val audio = draft.audio?.let {
            FileKit.copyCacheToStorage(it.file.fileName)
            NoteDto.Audio(
                fileName = it.file.fileName,
                duration = it.duration,
                title = it.title
            )
        }

        val note = NoteDto(
            roomId = draft.roomId,
            content = content,
            audio = audio,
            createdOn = createdOn,
            modifiedOn = createdOn
        )

        noteRepository.insertNote(note, files, links)
        roomRepository.updateRoomModified(draft.roomId)
    }

}
