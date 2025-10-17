package com.ravi.jot.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface JotEntryDao {

    @Upsert
    suspend fun upsertJotEntry(jotEntry: JotEntry)

    @Delete
    suspend fun deleteJotEntry(jotEntry: JotEntry)

    @Query("SELECT * FROM jotEntry")
    fun getAJotEntries(): Flow<List<JotEntry>>

    @Query("SELECT photoURI FROM jotEntry")
    fun getAJotPhotos(): Flow<List<String>>

    @Query("SELECT * FROM jotEntry WHERE id = :id LIMIT 1")
    fun getJotEntryById(id: Int): Flow<JotEntry?>
}