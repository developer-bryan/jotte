package com.jottie.message.persistence

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jottie.message.data.MediaDto
import com.jottie.message.data.LinkDto
import com.jottie.message.data.NoteDto
import com.jottie.message.data.RoomDto
import com.jottie.message.data.join.MediaJoin
import com.jottie.message.data.join.LinkJoin
import com.jottie.message.persistence.converter.LinkTypeConverter
import com.jottie.message.persistence.dao.MediaDao
import com.jottie.message.persistence.dao.NoteDao
import com.jottie.message.persistence.dao.RoomDao

@Database(
    entities = [
        RoomDto::class,
        NoteDto::class,
        MediaDto::class,
        MediaJoin::class,
        LinkDto::class,
        LinkJoin::class
    ],
    version = 1
)
@ConstructedBy(NotesDatabaseConstructor::class)
@TypeConverters(LinkTypeConverter::class)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun fileDao(): MediaDao
    abstract fun roomDao(): RoomDao
}