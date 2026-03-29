package com.jotte.data.usecase

import com.jotte.data.persistence.data.FullNote
import com.jotte.data.repository.NoteRepository

class GetNoteUseCase(private val noteRepository: NoteRepository) {

    suspend operator fun invoke(noteId: Long): FullNote = noteRepository.queryNote(noteId)

}