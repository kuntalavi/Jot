package com.ravi.jot.ui.vm

import androidx.lifecycle.ViewModel
import com.ravi.jot.data.JotEntryRepo

class AttachmentsVM(repo: JotEntryRepo): ViewModel() {

    private val _photos = repo.jotPhotos
    val photos = _photos
}