package com.jotte.data.persistence.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.jotte.data.persistence.data.FullNote
import com.jotte.data.persistence.data.MediaDto
import com.jotte.data.persistence.data.NoteDto
import com.jotte.data.persistence.data.join.MediaJoin
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

    @Upsert
    suspend fun insertNote(note: NoteDto): Long

    @Upsert
    suspend fun insertFile(file: MediaDto): Long

    @Upsert
    suspend fun insertFileJoin(file: MediaJoin): Long

    @Transaction
    suspend fun deleteNotes(noteIds: List<Long>): Int =
        noteIds.fold(0) { acc, value ->
            val rows = deleteNote(value)
            acc + rows
        }

    @Transaction
    suspend fun insertNote(
        note: NoteDto,
        files: List<MediaDto>
    ) {
        val noteId = insertNote(note)
        files.forEach {
            insertFile(it)
            val join = MediaJoin(noteId, it.mediaId)
            insertFileJoin(join)
        }
    }

    @Transaction
    suspend fun updateNote(
        note: NoteDto,
        files: List<MediaDto>
    ) {
        insertNote(note)
        files.forEach {
            insertFile(it)
            val join = MediaJoin(note.noteId, it.mediaId)
            insertFileJoin(join)
        }
    }

}