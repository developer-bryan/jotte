package com.jottie.message.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.jottie.message.persistence.dao.NoteDao
import com.jottie.message.persistence.NotesDatabase
import com.jottie.message.persistence.dao.MediaDao
import com.jottie.message.persistence.dao.RoomDao
import com.jottie.message.persistence.notesDatabaseBuilder
import com.jottie.message.repository.MediaRepository
import com.jottie.message.repository.MediaRepositoryImpl
import com.jottie.message.repository.NoteRepository
import com.jottie.message.repository.NoteRepositoryImpl
import com.jottie.message.repository.RoomRepository
import com.jottie.message.repository.RoomRepositoryImpl
import com.jottie.message.usecase.CheckShouldDeleteNoteUseCase
import com.jottie.message.usecase.CreateRoomUseCase
import com.jottie.message.usecase.DeleteAudioUseCase
import com.jottie.message.usecase.DeleteMediaUseCase
import com.jottie.message.usecase.DeleteNoteUseCase
import com.jottie.message.usecase.DeleteRoomUseCase
import com.jottie.message.usecase.GetNoteUseCase
import com.jottie.message.usecase.RenameRoomUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.qualifier.StringQualifier
import org.koin.dsl.module

internal fun databaseName() = StringQualifier("database_name")

fun provideNotesModule() = module {

    single<String>(
        qualifier = databaseName(),
        definition = { "jottie_notes.db" }
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

    factory<NoteRepository> { NoteRepositoryImpl(get()) }
    factory<RoomRepository> { RoomRepositoryImpl(get()) }
    factory<MediaRepository> { MediaRepositoryImpl(get()) }

    factory<CreateRoomUseCase> { CreateRoomUseCase(get()) }
    factory<DeleteAudioUseCase> { DeleteAudioUseCase(get(), get(), get(), get()) }
    factory<DeleteMediaUseCase> { DeleteMediaUseCase(get(), get(), get(), get()) }
    factory<DeleteNoteUseCase> { DeleteNoteUseCase(get(), get(), get()) }
    factory<DeleteRoomUseCase> { DeleteRoomUseCase(get(), get(), get()) }
    factory<RenameRoomUseCase> { RenameRoomUseCase(get()) }
    factory<GetNoteUseCase> { GetNoteUseCase(get()) }

    factory<CheckShouldDeleteNoteUseCase> { CheckShouldDeleteNoteUseCase() }
}