package com.ravi.jot

import android.content.Context
import androidx.room.Room
import com.ravi.jot.data.JotDatabase
import com.ravi.jot.data.JotEntryDao
import com.ravi.jot.data.JotEntryRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideJotDB(@ApplicationContext context: Context): JotDatabase {
        return Room.databaseBuilder(
            context,
            JotDatabase::class.java,
            "jot_DB"
        ).build()
    }

    @Provides
    fun provideJotEntrtyDao(d: JotDatabase): JotEntryDao = d.jotEntryDao()

    @Provides
    fun provideJotEntryRepo(jotEntryDao: JotEntryDao): JotEntryRepo {
        return JotEntryRepo(jotEntryDao)
    }
}