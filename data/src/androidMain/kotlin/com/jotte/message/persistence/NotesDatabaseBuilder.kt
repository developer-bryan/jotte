package com.jotte.message.persistence

import androidx.room.Room
import androidx.room.RoomDatabase
import com.jotte.core.ApplicationProvider

internal actual fun notesDatabaseBuilder(name: String): RoomDatabase.Builder<NotesDatabase> =
    Room.databaseBuilder<NotesDatabase>(
        context = ApplicationProvider.getApplication()!!,
        name = ApplicationProvider.getApplication()!!.getDatabasePath(name).absolutePath
    )