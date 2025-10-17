package com.ravi.jot.data

import kotlinx.coroutines.flow.Flow

class JotEntryRepo(private val jotEntryDao: JotEntryDao) {

    val jotEntries: Flow<List<JotEntry>> = jotEntryDao.getAJotEntries()

    val jotPhotos: Flow<List<String>> = jotEntryDao.getAJotPhotos()

    fun getJotEntryById(id: Int): Flow<JotEntry?> = jotEntryDao.getJotEntryById(id)

    suspend fun addJotEntry(jotEntry: JotEntry) = jotEntryDao.upsertJotEntry(jotEntry)

    suspend fun removeJotEntry(jotEntry: JotEntry) = jotEntryDao.deleteJotEntry(jotEntry)

}