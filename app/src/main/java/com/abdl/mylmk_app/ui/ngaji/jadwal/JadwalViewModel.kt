package com.abdl.mylmk_app.ui.ngaji.jadwal

import androidx.lifecycle.ViewModel
import com.abdl.mylmk_app.data.repository.MainRepository
import com.abdl.mylmk_app.data.source.local.entity.JadwalEntity

class JadwalViewModel constructor(private val repository: MainRepository) : ViewModel() {

    fun getJadwalUser(): List<JadwalEntity> = repository.getJadwalUser()
}