package com.jotte.data.usecase

import com.jotte.core.storageFile
import com.jotte.data.repository.MediaRepository
import com.jotte.data.repository.NoteRepository
import com.jotte.data.repository.RoomRepository
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.delete

class DeleteNoteUseCase(
    private val noteRepository: NoteRepository,
    private val roomRepository: RoomRepository,
    private val mediaRepository: MediaRepository,
) {

    suspend operator fun invoke(noteId: Long): Result<Boolean> =
        runCatching {
            val note = noteRepository.queryNote(noteId)

            val rowsDeleted = noteRepository.deleteNote(noteId)

            note.media?.forEach {
                mediaRepository.deleteMedia(it)
                FileKit.storageFile(it.fileName).delete(false)
            }

            roomRepository.updateRoomModified(note.note.roomId)
            rowsDeleted > 0
        }
}