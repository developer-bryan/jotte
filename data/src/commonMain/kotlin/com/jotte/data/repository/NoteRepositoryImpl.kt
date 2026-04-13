@file:OptIn(ExperimentalTime::class)

package com.jotte.data.repository

import com.jotte.data.persistence.dao.NoteDao
import com.jotte.data.persistence.data.FullNote
import com.jotte.data.persistence.data.MediaDto
import com.jotte.data.persistence.data.NoteDto
import kotlinx.coroutines.flow.Flow
import kotlin.time.ExperimentalTime

internal class NoteRepositoryImpl(private val dao: NoteDao) : NoteRepository {

    override fun observeNotes(roomId: Long): Flow<List<FullNote>> = dao.observeNotes(roomId)

    override suspend fun queryNotes(roomId: Long): List<FullNote> = dao.queryNotes(roomId)

    override suspend fun queryNote(noteId: Long): FullNote = dao.queryNote(noteId)

    override suspend fun queryNoteWithFilesByAudioId(audioId: String): FullNote? =
        dao.queryNoteWithFilesByAudioId(audioId)

    override suspend fun queryNoteByAudioId(audioId: String) = dao.queryNoteByAudioId(audioId)

    override suspend fun deleteNote(noteId: Long): Int = dao.deleteNote(noteId)

    override suspend fun deleteNote(noteIds: List<Long>): Int = dao.deleteNotes(noteIds)

    override suspend fun insertNote(
        note: NoteDto,
        files: List<MediaDto>,
    ) = dao.insertNote(note, files)

    override suspend fun updateNote(
        note: NoteDto,
        files: List<MediaDto>,
    ) = dao.updateNote(note, files)
}