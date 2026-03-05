package com.jotte.message.usecase

import com.jotte.core.VirtualFile
import com.jotte.message.repository.MediaRepository
import com.jotte.message.repository.NoteRepository
import com.jotte.message.repository.RoomRepository
import io.github.vinceglb.filekit.delete

class DeleteNoteUseCase(
    private val noteRepository: NoteRepository,
    private val roomRepository: RoomRepository,
    private val mediaRepository: MediaRepository,
) {

    suspend operator fun invoke(noteId: Long): Result<Boolean> = runCatching {
        val note = noteRepository.queryNote(noteId)

        val rowsDeleted = noteRepository.deleteNote(noteId)

        note.media?.forEach {
            mediaRepository.deleteMedia(it)
            VirtualFile(it.fileName, false).asFile().delete(false)
        }

        note.links?.let {
            val ids = it.map { link -> link.linkId }
            noteRepository.deleteLink(ids)
        }

        roomRepository.updateRoomModified(note.note.roomId)
        rowsDeleted > 0
    }
}