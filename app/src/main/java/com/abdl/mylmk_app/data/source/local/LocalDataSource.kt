package com.abdl.mylmk_app.data.source.local

import com.abdl.mylmk_app.data.source.local.entity.GuruEntity

interface LocalDataSource {
    fun getAllGuru(): List<GuruEntity>

    fun getGuruById(guruId: String): GuruEntity
}