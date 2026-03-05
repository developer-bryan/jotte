package com.jottie.message.persistence.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.jottie.message.data.MediaDto
import com.jottie.message.data.LinkDto
import com.jottie.message.data.NoteDto
import com.jottie.message.data.join.MediaJoin
import com.jottie.message.data.FullNote
import com.jottie.message.data.join.LinkJoin
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes WHERE roomId = :roomId ORDER BY createdOn DESC")
    fun observeNotes(roomId: Long): Flow<List<FullNote>>

    @Query("SELECT * FROM notes WHERE roomId = :roomId ORDER BY createdOn DESC")
    suspend fun queryNotes(roomId: Long): List<FullNote>

    @Query("SELECT * FROM notes WHERE noteId = :noteId")
    suspend fun queryNote(noteId: Long): FullNote

    @Query("SELECT * FROM notes WHERE audio_audioId = :audioId LIMIT 1")
    suspend fun queryNoteByAudioId(audioId: String): NoteDto?

    @Query("SELECT * FROM notes where audio_audioId = :audioId LIMIT 1")
    suspend fun queryNoteWithFilesByAudioId(audioId: String): FullNote

    @Query("DELETE FROM notes WHERE noteId = :noteId")
    suspend fun deleteNote(noteId: Long): Int

    @Query("DELETE FROM note_link_join WHERE linkId = :linkId")
    suspend fun deleteLink(linkId: String): Int

    @Transaction
    suspend fun deleteLink(linkIds: List<String>): Int {
        return linkIds.fold(
            initial = 0,
            operation = { acc, value -> acc + deleteLink(value) }
        )
    }

    @Upsert
    suspend fun insertNote(note: NoteDto): Long

    @Upsert
    suspend fun insertFile(file: MediaDto): Long

    @Upsert
    suspend fun insertFileJoin(file: MediaJoin): Long

    @Upsert
    suspend fun insertLink(link: LinkDto): Long

    @Upsert
    suspend fun insertLinkJoin(linkJoin: LinkJoin): Long

    @Transaction
    suspend fun deleteNotes(noteIds: List<Long>): Int {
        return noteIds.fold(0) { acc, value ->
            val rows = deleteNote(value)
            acc + rows
        }
    }

    @Transaction
    suspend fun insertNote(
        note: NoteDto,
        files: List<MediaDto>,
        links: List<LinkDto>
    ) {
        val noteId = insertNote(note)
        files.forEach {
            insertFile(it)
            val join = MediaJoin(noteId, it.mediaId)
            insertFileJoin(join)
        }
        links.forEach {
            insertLink(it)
            val join = LinkJoin(noteId, it.linkId)
            insertLinkJoin(join)
        }
    }

    @Transaction
    suspend fun updateNote(
        note: NoteDto,
        files: List<MediaDto>,
        links: List<LinkDto>
    ) {
        insertNote(note)
        files.forEach {
            insertFile(it)
            val join = MediaJoin(note.noteId, it.mediaId)
            insertFileJoin(join)
        }
        links.forEach {
            insertLink(it)
            val join = LinkJoin(note.noteId, it.linkId)
            insertLinkJoin(join)
        }
    }

}