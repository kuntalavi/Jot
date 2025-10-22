package com.ravi.jot.presentation.vm

import androidx.lifecycle.ViewModel
import com.ravi.jot.data.JotEntryRepo

class AttachmentsVM(repo: JotEntryRepo): ViewModel() {

    private val _photos = repo.jotPhotos
    val photos = _photos
}