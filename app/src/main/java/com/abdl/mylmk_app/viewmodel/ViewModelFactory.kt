package com.abdl.mylmk_app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.abdl.mylmk_app.data.repository.MainRepository
import com.abdl.mylmk_app.ui.home.program.ProgramViewModel

class ViewModelFactory constructor(private val repository: MainRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(ProgramViewModel::class.java) -> {
                return ProgramViewModel(repository) as T
            }
            else -> throw Throwable("Unknown ViewModel class:" + modelClass.name)
        }
    }
}