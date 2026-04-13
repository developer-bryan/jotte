package com.jotte.editor.usecase

import com.jotte.core.copyCacheToStorage
import com.jotte.core.storageFile
import com.jotte.data.persistence.data.LinkDto
import com.jotte.data.persistence.data.MediaDto
import com.jotte.data.persistence.data.NoteDto
import com.jotte.data.repository.MediaRepository
import com.jotte.data.repository.NoteRepository
import com.jotte.data.repository.RoomRepository
import com.jotte.editor.model.state.DraftState
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.delete
import io.github.vinceglb.filekit.name
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

internal class UpdateNoteUseCase(
    private val noteRepository: NoteRepository,
    private val roomRepository: RoomRepository,
    private val mediaRepository: MediaRepository
) {

    @OptIn(ExperimentalTime::class)
    @Suppress("LongMethod")
    suspend operator fun invoke(
        draft: DraftState,
        content: String
    ) {
        val modifiedOn = Clock.System.now().toEpochMilliseconds()

        checkNotNull(draft.noteId)

        // FETCH ORIGINAL NOTE
        val originalNote = noteRepository.queryNote(draft.noteId)
        val originalMedia = (originalNote.media ?: emptyList())
        val originalLinks = (originalNote.links ?: emptyList())
        var updatedNote = originalNote.note.copy(modifiedOn = modifiedOn)

        // MARK MEDIAS THAT EXIST IN DRAFT BUT NOT ORIGINAL
        val newMedia =
            draft.media
                .filter { new -> originalMedia.none { old -> old.fileName == new.name } }
                .map { MediaDto(fileName = it.name) }

        // MARK MEDIAS THAT EXIST IN ORIGINAL BUT NOT IN DRAFT
        val mediaToDelete =
            originalMedia.filter { old -> draft.media.none { new -> old.fileName == new.name } }

        val links =
            draft.links.map {
                val link =
                    LinkDto(
                        link = it.link,
                        linkType = it.type
                    )
                it.id?.let { id -> link.copy(linkId = id) } ?: link
            }

        val linksToRemove =
            originalLinks
                .filter { old -> draft.links.none { new -> old.linkId == new.id } }
                .map { it.linkId }

        // SET CONTENT
        updatedNote = updatedNote.copy(content = NoteDto.Content(content))

        // SET AUDIO: ADD | REPLACE | REMOVE
        if (draft.audio != null) {
            if (originalNote.note.audio != null) {
                // DELETE OLD AUDIO FILE AND SAVE NEW AUDIO FILE
                if (originalNote.note.audio!!.fileName != draft.audio.file.name) {
                    FileKit.storageFile(originalNote.note.audio!!.fileName).delete(false)
                    FileKit.copyCacheToStorage(draft.audio.file.name)
                }
            } else {
                // SAVE NEW AUDIO FILE
                FileKit.copyCacheToStorage(draft.audio.file.name)
            }

            val updatedAudio =
                NoteDto.Audio(
                    fileName = draft.audio.file.name,
                    duration = draft.audio.duration,
                    title = draft.audio.title
                )
            updatedNote = updatedNote.copy(audio = updatedAudio)
        } else {
            // DELETE OLD AUDIO FILE AND SET AUDIO TO NULL
            if (originalNote.note.audio != null) {
                FileKit.storageFile(originalNote.note.audio!!.fileName).delete(false)
                updatedNote = updatedNote.copy(audio = null)
            }
        }

        // WRITE NEW ATTACHMENTS TO FS
        newMedia.forEach { FileKit.copyCacheToStorage(it.fileName) }
        noteRepository.updateNote(updatedNote, newMedia, links)

        // REMOVE OLD MEDIA FROM DATABASE
        mediaRepository.deleteMedia(mediaToDelete)

        // REMOVE OLD LINKS FROM DATABASE
        if (linksToRemove.isNotEmpty()) {
            noteRepository.deleteLink(linksToRemove)
        }

        // REMOVE OLD MEDIA FROM FS
        mediaToDelete.forEach { FileKit.storageFile(it.fileName).delete(false) }

        roomRepository.updateRoomModified(draft.roomId)
    }

}