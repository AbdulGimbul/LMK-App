package com.abdl.mylmk_app.ui.home.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.abdl.mylmk_app.data.repository.LmkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: LmkRepository) : ViewModel() {

    fun getJadwalGuru(idGuru: Int) = repository.getJadwalGuru(idGuru).asLiveData()

}