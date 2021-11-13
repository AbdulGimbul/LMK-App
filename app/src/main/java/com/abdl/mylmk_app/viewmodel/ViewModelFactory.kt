package com.abdl.mylmk_app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.abdl.mylmk_app.data.repository.MainRepository
import com.abdl.mylmk_app.ui.home.detail.DetailViewModel
import com.abdl.mylmk_app.ui.home.program.ProgramViewModel
import com.abdl.mylmk_app.ui.ngaji.jadwal.JadwalViewModel

class ViewModelFactory constructor(private val repository: MainRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                return DetailViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ProgramViewModel::class.java) -> {
                return ProgramViewModel(repository) as T
            }
            modelClass.isAssignableFrom(JadwalViewModel::class.java) -> {
                return JadwalViewModel(repository) as T
            }
            else -> throw Throwable("Unknown ViewModel class:" + modelClass.name)
        }
    }
}