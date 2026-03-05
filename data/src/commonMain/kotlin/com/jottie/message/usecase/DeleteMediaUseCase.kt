package com.jottie.message.usecase

import com.jottie.core.storageFile
import com.jottie.message.data.MediaDto
import com.jottie.message.repository.MediaRepository
import com.jottie.message.repository.NoteRepository
import com.jottie.message.repository.RoomRepository
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.delete

class DeleteMediaUseCase(
    private val repository: MediaRepository,
    private val noteRepository: NoteRepository,
    private val roomRepository: RoomRepository,
    private val checkIfShouldDeleteNoteUseCase: CheckShouldDeleteNoteUseCase
) {

    suspend operator fun invoke(media: MediaDto): Result<Boolean> {
        return runCatching {
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
        }
            .mapCatching { rowsDeleted ->
                val file = FileKit.storageFile(media.fileName)
                file.delete(false)
                rowsDeleted > 0
            }
    }

}