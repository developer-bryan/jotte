package com.jotte.data.persistence

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jotte.data.persistence.data.LinkDto
import com.jotte.data.persistence.data.MediaDto
import com.jotte.data.persistence.data.NoteDto
import com.jotte.data.persistence.data.RoomDto
import com.jotte.data.persistence.data.WhiteboardDto
import com.jotte.data.persistence.data.join.LinkJoin
import com.jotte.data.persistence.data.join.MediaJoin
import com.jotte.data.persistence.converter.LinkTypeConverter
import com.jotte.data.persistence.converter.PathConverter
import com.jotte.data.persistence.dao.MediaDao
import com.jotte.data.persistence.dao.NoteDao
import com.jotte.data.persistence.dao.RoomDao
import com.jotte.data.persistence.dao.WhiteboardDao

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
@TypeConverters(LinkTypeConverter::class, PathConverter::class)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

    abstract fun fileDao(): MediaDao

    abstract fun roomDao(): RoomDao

    abstract fun whiteboardDao(): WhiteboardDao
}