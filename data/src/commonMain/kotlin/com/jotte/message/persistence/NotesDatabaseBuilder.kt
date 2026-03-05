package com.jotte.message.persistence

import androidx.room.RoomDatabase

internal expect fun notesDatabaseBuilder(name: String): RoomDatabase.Builder<NotesDatabase>
