package com.ravi.jot.ui.vm

import androidx.lifecycle.ViewModel
import com.ravi.jot.data.JotEntryRepo

class JotVM(repo: JotEntryRepo) : ViewModel() {

    private val _entries = repo.jotEntries
    val entries = _entries
}