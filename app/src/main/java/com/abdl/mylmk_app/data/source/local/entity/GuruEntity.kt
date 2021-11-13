package com.abdl.mylmk_app.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_guru")
data class GuruEntity(
    @PrimaryKey var guruId: String,
    var username: String,
    var nama: String,
    var jk: String,
    var tempatLahir: String,
    var tanggalLahir: String,
    var alamat: String,
    var imagePath: String
)
