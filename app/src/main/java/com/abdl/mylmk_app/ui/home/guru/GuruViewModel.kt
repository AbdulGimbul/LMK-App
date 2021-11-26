package com.abdl.mylmk_app.ui.home.guru

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.abdl.mylmk_app.data.repository.LmkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GuruViewModel @Inject constructor(private val repository: LmkRepository) : ViewModel() {

    val guru = repository.getGuru().asLiveData()

}