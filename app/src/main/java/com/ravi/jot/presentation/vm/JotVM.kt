package com.ravi.jot.presentation.vm

import androidx.lifecycle.ViewModel
import com.ravi.jot.data.JotEntryRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class JotVM @Inject constructor(
    repo: JotEntryRepo
) : ViewModel() {

    private val _entries = repo.jotEntries
    val entries = _entries
}