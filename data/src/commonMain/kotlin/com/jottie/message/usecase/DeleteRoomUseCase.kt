package com.jottie.message.usecase

import com.jottie.core.storageFile
import com.jottie.message.repository.MediaRepository
import com.jottie.message.repository.NoteRepository
import com.jottie.message.repository.RoomRepository
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.delete

class DeleteRoomUseCase(
    private val roomRepository: RoomRepository,
    private val noteRepository: NoteRepository,
    private val mediaRepository: MediaRepository
) {

    suspend operator fun invoke(roomId: Long): Result<Boolean> = runCatching {
        val room = roomRepository.queryRoom(roomId)
        val notes = noteRepository.queryNotes(room.id)

        val noteIds = notes.map { it.note.noteId }

        val files = notes.flatMap { it.media ?: emptyList() }

        roomRepository.deleteRoom(room)
        noteRepository.deleteNote(noteIds)
        mediaRepository.deleteMedia(files)

        files.forEach {
            val file = FileKit.storageFile(it.fileName)
            file.delete(false)
        }

        true
    }
}