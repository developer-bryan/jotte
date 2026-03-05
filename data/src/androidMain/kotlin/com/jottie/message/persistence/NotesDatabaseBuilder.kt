package com.jottie.message.persistence

import androidx.room.Room
import androidx.room.RoomDatabase
import com.jottie.core.ApplicationProvider

internal actual fun notesDatabaseBuilder(name: String): RoomDatabase.Builder<NotesDatabase> =
    Room.databaseBuilder<NotesDatabase>(
        context = ApplicationProvider.getApplication()!!,
        name = ApplicationProvider.getApplication()!!.getDatabasePath(name).absolutePath
    )