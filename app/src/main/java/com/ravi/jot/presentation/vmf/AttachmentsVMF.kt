package com.ravi.jot.presentation.vmf

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ravi.jot.data.JotEntryRepo
import com.ravi.jot.presentation.vm.AttachmentsVM

class AttachmentsVMF(private val repo: JotEntryRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AttachmentsVM::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AttachmentsVM(repo) as T
        }
        throw IllegalArgumentException("Unknown VM")
    }
}