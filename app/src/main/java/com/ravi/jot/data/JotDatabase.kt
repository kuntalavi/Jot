package com.ravi.jot.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [JotEntry::class],
    version = 1
)
abstract class JotDatabase : RoomDatabase() {
    abstract fun jotEntryDao(): JotEntryDao

    companion object {
        @Volatile
        private var INSTANCE: JotDatabase? = null

        fun getJotDB(context: Context): JotDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    JotDatabase::class.java,
                    "jot_DB"
                ).build().also { INSTANCE = it }
            }
        }
    }
}