package com.jottie.message.repository

import com.jottie.message.data.MediaDto
import com.jottie.message.data.LinkDto
import com.jottie.message.data.NoteDto
import com.jottie.message.data.FullNote
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun observeNotes(roomId: Long): Flow<List<FullNote>>

    suspend fun queryNotes(roomId: Long): List<FullNote>
    suspend fun queryNote(noteId: Long): FullNote
    suspend fun queryNoteWithFilesByAudioId(audioId: String): FullNote?
    suspend fun queryNoteByAudioId(audioId: String): NoteDto?

    suspend fun deleteNote(noteId: Long): Int
    suspend fun deleteNote(noteIds: List<Long>): Int

    suspend fun deleteLink(linkId: String): Int
    suspend fun deleteLink(linkIds: List<String>): Int

    suspend fun insertNote(
        note: NoteDto,
        files: List<MediaDto>,
        links: List<LinkDto>
    )

    suspend fun updateNote(
        note: NoteDto,
        files: List<MediaDto>,
        links: List<LinkDto>
    )
}