@file:OptIn(ExperimentalTime::class)

package com.jotte.message.repository

import com.jotte.message.data.FullNote
import com.jotte.message.data.LinkDto
import com.jotte.message.data.MediaDto
import com.jotte.message.data.NoteDto
import com.jotte.message.persistence.dao.NoteDao
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

    override suspend fun deleteLink(linkId: String): Int = dao.deleteLink(linkId)

    override suspend fun deleteLink(linkIds: List<String>): Int = dao.deleteLink(linkIds)

    override suspend fun insertNote(
        note: NoteDto,
        files: List<MediaDto>,
        links: List<LinkDto>
    ) = dao.insertNote(note, files, links)

    override suspend fun updateNote(
        note: NoteDto,
        files: List<MediaDto>,
        links: List<LinkDto>
    ) = dao.updateNote(note, files, links)
}