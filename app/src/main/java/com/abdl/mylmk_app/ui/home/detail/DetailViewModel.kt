package com.abdl.mylmk_app.ui.home.detail

import androidx.lifecycle.ViewModel
import com.abdl.mylmk_app.data.repository.MainRepository

class DetailViewModel(private val repository: MainRepository) : ViewModel() {
    private lateinit var guruId: String

    fun setSelectedGuru(guruId: String) {
        this.guruId = guruId
    }

//    fun getGuru(): GuruItem = repository.getGuruById(guruId)

}