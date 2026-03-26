package com.jotte.message.usecase

import com.jotte.core.storageFile
import com.jotte.message.data.MediaDto
import com.jotte.message.repository.MediaRepository
import com.jotte.message.repository.NoteRepository
import com.jotte.message.repository.RoomRepository
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.delete

class DeleteMediaUseCase(
    private val repository: MediaRepository,
    private val noteRepository: NoteRepository,
    private val roomRepository: RoomRepository,
    private val checkIfShouldDeleteNoteUseCase: CheckShouldDeleteNoteUseCase
) {

    suspend operator fun invoke(media: MediaDto): Result<Boolean> =
        runCatching {
            val join = repository.queryMediaJoin(media.mediaId)
            checkNotNull(join)

            val noteId = join.noteId
            val note = noteRepository.queryNote(noteId)

            val shouldDeleteNote =
                checkIfShouldDeleteNoteUseCase.shouldDeleteOnMediaRemoval(note, media.mediaId)

            if (shouldDeleteNote) {
                noteRepository.deleteNote(noteId)
            }

            repository.deleteMedia(media)
            roomRepository.updateRoomModified(note.note.roomId)
        }.mapCatching { rowsDeleted ->
            val file = FileKit.storageFile(media.fileName)
            file.delete(false)
            rowsDeleted > 0
        }

}