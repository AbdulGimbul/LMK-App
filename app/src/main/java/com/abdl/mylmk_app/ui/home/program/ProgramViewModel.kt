package com.abdl.mylmk_app.ui.home.program

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.abdl.mylmk_app.data.repository.LmkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProgramViewModel @Inject constructor(private val repository: LmkRepository) : ViewModel() {

    val program = repository.getProgram().asLiveData()
}