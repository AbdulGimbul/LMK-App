package com.abdl.mylmk_app.ui.home.guru

import androidx.lifecycle.ViewModel
import com.abdl.mylmk_app.data.repository.MainRepository
import com.abdl.mylmk_app.data.source.local.entity.GuruEntity

class GuruViewModel constructor(private val repository: MainRepository) : ViewModel() {

    fun getAllGuru(): List<GuruEntity> = repository.getAllGuru()
}