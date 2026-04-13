@file:OptIn(ExperimentalTime::class)

package com.jotte.editor.usecase

import com.jotte.core.copyCacheToStorage
import com.jotte.data.persistence.data.LinkDto
import com.jotte.data.persistence.data.MediaDto
import com.jotte.data.persistence.data.NoteDto
import com.jotte.data.repository.NoteRepository
import com.jotte.data.repository.RoomRepository
import com.jotte.editor.model.state.DraftState
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.name
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

internal class CreateNoteUseCase(
    private val noteRepository: NoteRepository,
    private val roomRepository: RoomRepository
) {

    suspend operator fun invoke(draft: DraftState, content: String) {
        val createdOn = Clock.System.now().toEpochMilliseconds()
        val files = ArrayList<MediaDto>()
        val links = ArrayList<LinkDto>()

        draft.media.map {
            FileKit.copyCacheToStorage(it.name)
            val file = MediaDto(fileName = it.name)
            files.add(file)
        }

        draft.links.map {
            val link =
                LinkDto(
                    link = it.link,
                    linkType = it.type
                )
            links.add(link)
        }

        val content = NoteDto.Content(content)
        val audio =
            draft.audio?.let {
                FileKit.copyCacheToStorage(it.file.name)
                NoteDto.Audio(
                    fileName = it.file.name,
                    duration = it.duration,
                    title = it.title
                )
            }

        val note =
            NoteDto(
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
