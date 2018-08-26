package com.praveen.xoom.ui

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.praveen.xoom.repo.XoomRepository
import javax.inject.Inject

class XoomViewModelFactory @Inject constructor(private val xoomRepository: XoomRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(XoomViewModel::class.java)) {
            return XoomViewModel(xoomRepository) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}