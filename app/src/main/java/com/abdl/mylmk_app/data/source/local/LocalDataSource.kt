package com.abdl.mylmk_app.data.source.local

import com.abdl.mylmk_app.data.source.local.entity.GuruEntity
import com.abdl.mylmk_app.data.source.local.entity.ProgramEntity

interface LocalDataSource {
    fun getAllGuru(): List<GuruEntity>

    fun getGuruById(guruId: String): GuruEntity

    fun getAllProgram(): List<ProgramEntity>

//    fun getJadwalUser(): List<JadwalEntity>
}