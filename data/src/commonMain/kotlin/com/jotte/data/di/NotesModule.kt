package com.jotte.data.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.jotte.data.persistence.NotesDatabase
import com.jotte.data.persistence.dao.MediaDao
import com.jotte.data.persistence.dao.NoteDao
import com.jotte.data.persistence.dao.RoomDao
import com.jotte.data.persistence.dao.WhiteboardDao
import com.jotte.data.persistence.notesDatabaseBuilder
import com.jotte.data.repository.MediaRepository
import com.jotte.data.repository.MediaRepositoryImpl
import com.jotte.data.repository.NoteRepository
import com.jotte.data.repository.NoteRepositoryImpl
import com.jotte.data.repository.RoomRepository
import com.jotte.data.repository.RoomRepositoryImpl
import com.jotte.data.repository.WhiteboardRepository
import com.jotte.data.repository.WhiteboardRepositoryImpl
import com.jotte.data.usecase.CheckShouldDeleteNoteUseCase
import com.jotte.data.usecase.CreateRoomUseCase
import com.jotte.data.usecase.DeleteAudioUseCase
import com.jotte.data.usecase.DeleteMediaUseCase
import com.jotte.data.usecase.DeleteNoteUseCase
import com.jotte.data.usecase.DeleteRoomUseCase
import com.jotte.data.usecase.GetNoteUseCase
import com.jotte.data.usecase.GetWhiteboardUseCase
import com.jotte.data.usecase.RenameRoomUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.qualifier.StringQualifier
import org.koin.dsl.module

internal fun databaseName() = StringQualifier("database_name")

fun provideNotesModule() =
    module {

        single<String>(
            qualifier = databaseName(),
            definition = { "jotte_notes.db" }
        )

        single<NotesDatabase> {
            notesDatabaseBuilder(get<String>(databaseName()))
                .setQueryCoroutineContext(Dispatchers.IO)
                .setDriver(BundledSQLiteDriver())
                .build()
        }

        single<NoteDao> { get<NotesDatabase>().noteDao() }
        single<RoomDao> { get<NotesDatabase>().roomDao() }
        single<MediaDao> { get<NotesDatabase>().fileDao() }
        single<WhiteboardDao> { get<NotesDatabase>().whiteboardDao() }

        factory<NoteRepository> { NoteRepositoryImpl(get()) }
        factory<RoomRepository> { RoomRepositoryImpl(get()) }
        factory<MediaRepository> { MediaRepositoryImpl(get()) }
        factory<WhiteboardRepository> { WhiteboardRepositoryImpl(get()) }

        factory<CreateRoomUseCase> { CreateRoomUseCase(get()) }
        factory<DeleteAudioUseCase> { DeleteAudioUseCase(get(), get(), get(), get()) }
        factory<DeleteMediaUseCase> { DeleteMediaUseCase(get(), get(), get(), get()) }
        factory<DeleteNoteUseCase> { DeleteNoteUseCase(get(), get(), get()) }
        factory<DeleteRoomUseCase> { DeleteRoomUseCase(get(), get(), get()) }
        factory<RenameRoomUseCase> { RenameRoomUseCase(get()) }
        factory<GetNoteUseCase> { GetNoteUseCase(get()) }
        factory<GetWhiteboardUseCase> { GetWhiteboardUseCase(get()) }

        factory<CheckShouldDeleteNoteUseCase> { CheckShouldDeleteNoteUseCase() }
    }