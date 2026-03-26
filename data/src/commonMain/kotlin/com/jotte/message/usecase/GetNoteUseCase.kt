package com.jotte.message.usecase

import com.jotte.message.data.FullNote
import com.jotte.message.repository.NoteRepository

class GetNoteUseCase(private val noteRepository: NoteRepository) {

    suspend operator fun invoke(noteId: Long): FullNote = noteRepository.queryNote(noteId)

}