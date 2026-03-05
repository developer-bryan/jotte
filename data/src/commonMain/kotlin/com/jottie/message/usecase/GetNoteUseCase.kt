package com.jottie.message.usecase

import com.jottie.message.data.FullNote
import com.jottie.message.repository.NoteRepository

class GetNoteUseCase(private val noteRepository: NoteRepository) {

    suspend operator fun invoke(noteId: Long): FullNote {
        return noteRepository.queryNote(noteId)
    }

}