package com.jotte.message.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.jotte.message.persistence.NotesDatabase
import com.jotte.message.persistence.dao.MediaDao
import com.jotte.message.persistence.dao.NoteDao
import com.jotte.message.persistence.dao.RoomDao
import com.jotte.message.persistence.dao.WhiteboardDao
import com.jotte.message.persistence.notesDatabaseBuilder
import com.jotte.message.repository.MediaRepository
import com.jotte.message.repository.MediaRepositoryImpl
import com.jotte.message.repository.NoteRepository
import com.jotte.message.repository.NoteRepositoryImpl
import com.jotte.message.repository.RoomRepository
import com.jotte.message.repository.RoomRepositoryImpl
import com.jotte.message.repository.WhiteboardRepository
import com.jotte.message.repository.WhiteboardRepositoryImpl
import com.jotte.message.usecase.CheckShouldDeleteNoteUseCase
import com.jotte.message.usecase.CreateRoomUseCase
import com.jotte.message.usecase.DeleteAudioUseCase
import com.jotte.message.usecase.DeleteMediaUseCase
import com.jotte.message.usecase.DeleteNoteUseCase
import com.jotte.message.usecase.DeleteRoomUseCase
import com.jotte.message.usecase.GetNoteUseCase
import com.jotte.message.usecase.GetWhiteboardUseCase
import com.jotte.message.usecase.RenameRoomUseCase
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