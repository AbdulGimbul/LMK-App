package com.abdl.mylmk_app.data.source.remote.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Guru(
    var id_guru: String,
    var username: String,
    var nama: String,
    var jk: String,
    var tempat_lahir: String,
    var tgl_lahir: String,
    var alamat: String,
    var avatar: String,
) : Parcelable

data class GuruResponse(
    var guru: List<Guru>
)