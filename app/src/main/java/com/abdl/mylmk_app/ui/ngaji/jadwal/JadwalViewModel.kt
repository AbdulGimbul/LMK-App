package com.abdl.mylmk_app.ui.ngaji.jadwal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.abdl.mylmk_app.data.repository.LmkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class JadwalViewModel @Inject constructor(private val repository: LmkRepository) : ViewModel() {

    fun getJadwal(idUser: Int) = repository.getJadwal(idUser).asLiveData()
}