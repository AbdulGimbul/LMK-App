package com.abdl.mylmk_app.data.source.local.entity


data class ProgramEntity(
    val idInfo: String,

    val judul: String,

    val deskripsi: String,

    val gambar: String,

    val type: String,

    var expand: Boolean = false
)
