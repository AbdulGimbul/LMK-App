package com.abdl.mylmk_app.ui.home.program

import androidx.lifecycle.ViewModel
import com.abdl.mylmk_app.data.repository.MainRepository
import com.abdl.mylmk_app.data.source.local.entity.ProgramEntity

class ProgramViewModel constructor(private val repository: MainRepository) : ViewModel() {

    fun getAllProgram(): List<ProgramEntity> = repository.getAllProgram()
}