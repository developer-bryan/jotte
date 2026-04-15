package com.jotte.data.usecase

import com.jotte.core.storageFile
import com.jotte.data.persistence.data.FullNote
import com.jotte.data.repository.NoteRepository
import com.jotte.data.repository.RoomRepository
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.delete

/**
 * This class manages the deletion of an Audio note.
 * It covers scenarios where audio is and is-not the only piece of content within a note.
 * For the former, it will delete the entire note. The later will simply update an existing note with
 * the exclusion of audio content.
 *
 * Operations ~
 * 1) Delete Audio OR Delete entire note
 * 3) Delete backing file from file system
 * 4) Update room lastModified
 *
 * @param noteRepository the repository layer managing access to a notes dao
 * @param roomRepository the repository layer managing access to the room dao
 * @param checkShouldDeleteNoteUseCase a usecase that checks if we should delete whole note
 */
class DeleteAudioUseCase(
    private val noteRepository: NoteRepository,
    private val roomRepository: RoomRepository,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val checkShouldDeleteNoteUseCase: CheckShouldDeleteNoteUseCase
) {

    suspend operator fun invoke(audioId: String): Result<Unit> =
        runCatching {

            val note: FullNote? = noteRepository.queryNoteWithFilesByAudioId(audioId)
            checkNotNull(note)

            val audio = note.note.audio
            checkNotNull(audio)

            val audioFile = FileKit.storageFile(audio.fileName)

            val shouldDeleteNote = checkShouldDeleteNoteUseCase.shouldDeleteOnAudioRemoval(note)
            val noteId = note.note.noteId
            val roomId = note.note.roomId
            val media = note.media ?: emptyList()

            if (shouldDeleteNote) {
                deleteNoteUseCase(noteId)
            } else {
                val updatedNote = note.note.copy(audio = null)
                noteRepository.insertNote(updatedNote, media)
            }

            audioFile.delete(false)

            roomRepository.updateRoomModified(roomId)
        }

}