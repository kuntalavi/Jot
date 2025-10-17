package com.ravi.jot.ui.vm

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ravi.jot.data.JotEntry
import com.ravi.jot.data.JotEntryRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class NewJotVM(private val repo: JotEntryRepo) : ViewModel() {

    var more by mutableStateOf(false)
        private set

    var sImageURI by mutableStateOf<Uri?>(null)

    fun showM() {
        more = true
    }

    fun hM() {
        more = false
    }

    fun getJotEntrybyId(i: Int, e: String, month: String, date: Int): Flow<JotEntry> {
        if (i == -1) {
            return flowOf(
                JotEntry(
                    e = e,
                    month = month,
                    date = date,
                    photoURI = ""
                )
            )
        }
        return repo.getJotEntryById(i).map {
            it ?: JotEntry(
                e = e,
                month = month,
                date = date,
                photoURI = ""
            )
        }
    }

    fun addJotEntry(jotEntry: JotEntry) {
        if (jotEntry.content != "") {
            viewModelScope.launch {
                repo.addJotEntry(jotEntry)
            }
        }
    }

    fun removeJotEntry(jotEntry: JotEntry) {
        viewModelScope.launch {
            repo.removeJotEntry(jotEntry)
        }
    }
}