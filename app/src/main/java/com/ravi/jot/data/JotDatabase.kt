package com.ravi.jot.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [JotEntry::class],
    version = 1,
    exportSchema = false
)
abstract class JotDatabase : RoomDatabase() {
    abstract fun jotEntryDao(): JotEntryDao
}