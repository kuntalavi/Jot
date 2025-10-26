package com.ravi.jot.presentation.vm

import androidx.lifecycle.ViewModel
import com.ravi.jot.data.JotEntryRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AttachmentsVM @Inject constructor(
    repo: JotEntryRepo
) : ViewModel() {

    private val _photos = repo.jotPhotos
    val photos = _photos
}