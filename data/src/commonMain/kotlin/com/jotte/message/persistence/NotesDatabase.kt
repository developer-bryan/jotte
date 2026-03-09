package com.jotte.message.persistence

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jotte.message.data.MediaDto
import com.jotte.message.data.LinkDto
import com.jotte.message.data.NoteDto
import com.jotte.message.data.RoomDto
import com.jotte.message.data.WhiteboardDto
import com.jotte.message.data.join.MediaJoin
import com.jotte.message.data.join.LinkJoin
import com.jotte.message.persistence.converter.LinkTypeConverter
import com.jotte.message.persistence.dao.MediaDao
import com.jotte.message.persistence.dao.NoteDao
import com.jotte.message.persistence.dao.RoomDao

@Database(
    entities = [
        RoomDto::class,
        NoteDto::class,
        MediaDto::class,
        MediaJoin::class,
        LinkDto::class,
        LinkJoin::class,
        WhiteboardDto::class
    ],
    version = 1
)
@ConstructedBy(NotesDatabaseConstructor::class)
@TypeConverters(LinkTypeConverter::class)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun fileDao(): MediaDao
    abstract fun roomDao(): RoomDao
    abstract fun whiteboardDao(): RoomDao
}