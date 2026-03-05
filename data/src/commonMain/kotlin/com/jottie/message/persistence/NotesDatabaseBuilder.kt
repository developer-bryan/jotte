package com.jottie.message.persistence

import androidx.room.RoomDatabase

internal expect fun notesDatabaseBuilder(name: String): RoomDatabase.Builder<NotesDatabase>
